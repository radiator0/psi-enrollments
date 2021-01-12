import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IEnrollmentDate } from 'app/shared/model/enrollment-date.model';
import { getEntities as getEnrollmentDates } from 'app/entities/enrollment-date/enrollment-date.reducer';
import { ISpecialty } from 'app/shared/model/specialty.model';
import { getEntities as getSpecialties } from 'app/entities/specialty/specialty.reducer';
import { IStudent } from 'app/shared/model/student.model';
import { getEntities as getStudents } from 'app/entities/student/student.reducer';
import { getEntity, updateEntity, createEntity, reset } from './enrollment-right.reducer';
import { IEnrollmentRight } from 'app/shared/model/enrollment-right.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IEnrollmentRightUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EnrollmentRightUpdate = (props: IEnrollmentRightUpdateProps) => {
  const [enrollmentDateId, setEnrollmentDateId] = useState('0');
  const [specialtyId, setSpecialtyId] = useState('0');
  const [studentId, setStudentId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { enrollmentRightEntity, enrollmentDates, specialties, students, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/enrollment-right');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getEnrollmentDates();
    props.getSpecialties();
    props.getStudents();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...enrollmentRightEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="enrollmentsApp.enrollmentRight.home.createOrEditLabel">
            <Translate contentKey="enrollmentsApp.enrollmentRight.home.createOrEditLabel">Create or edit a EnrollmentRight</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : enrollmentRightEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="enrollment-right-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="enrollment-right-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="startDateLabel" for="enrollment-right-startDate">
                  <Translate contentKey="enrollmentsApp.enrollmentRight.startDate">Start Date</Translate>
                </Label>
                <AvField
                  id="enrollment-right-startDate"
                  type="date"
                  className="form-control"
                  name="startDate"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label for="enrollment-right-enrollmentDate">
                  <Translate contentKey="enrollmentsApp.enrollmentRight.enrollmentDate">Enrollment Date</Translate>
                </Label>
                <AvInput id="enrollment-right-enrollmentDate" type="select" className="form-control" name="enrollmentDateId">
                  <option value="" key="0" />
                  {enrollmentDates
                    ? enrollmentDates.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="enrollment-right-specialty">
                  <Translate contentKey="enrollmentsApp.enrollmentRight.specialty">Specialty</Translate>
                </Label>
                <AvInput id="enrollment-right-specialty" type="select" className="form-control" name="specialtyId">
                  <option value="" key="0" />
                  {specialties
                    ? specialties.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="enrollment-right-student">
                  <Translate contentKey="enrollmentsApp.enrollmentRight.student">Student</Translate>
                </Label>
                <AvInput id="enrollment-right-student" type="select" className="form-control" name="studentId">
                  <option value="" key="0" />
                  {students
                    ? students.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/enrollment-right" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  enrollmentDates: storeState.enrollmentDate.entities,
  specialties: storeState.specialty.entities,
  students: storeState.student.entities,
  enrollmentRightEntity: storeState.enrollmentRight.entity,
  loading: storeState.enrollmentRight.loading,
  updating: storeState.enrollmentRight.updating,
  updateSuccess: storeState.enrollmentRight.updateSuccess,
});

const mapDispatchToProps = {
  getEnrollmentDates,
  getSpecialties,
  getStudents,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EnrollmentRightUpdate);

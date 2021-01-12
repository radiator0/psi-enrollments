import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IStudent } from 'app/shared/model/student.model';
import { getEntities as getStudents } from 'app/entities/student/student.reducer';
import { IClassGroup } from 'app/shared/model/class-group.model';
import { getEntities as getClassGroups } from 'app/entities/class-group/class-group.reducer';
import { getEntity, updateEntity, createEntity, reset } from './enrollment.reducer';
import { IEnrollment } from 'app/shared/model/enrollment.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IEnrollmentUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EnrollmentUpdate = (props: IEnrollmentUpdateProps) => {
  const [studentId, setStudentId] = useState('0');
  const [classGroupId, setClassGroupId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { enrollmentEntity, students, classGroups, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/enrollment');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getStudents();
    props.getClassGroups();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...enrollmentEntity,
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
          <h2 id="enrollmentsApp.enrollment.home.createOrEditLabel">
            <Translate contentKey="enrollmentsApp.enrollment.home.createOrEditLabel">Create or edit a Enrollment</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : enrollmentEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="enrollment-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="enrollment-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="dateLabel" for="enrollment-date">
                  <Translate contentKey="enrollmentsApp.enrollment.date">Date</Translate>
                </Label>
                <AvField
                  id="enrollment-date"
                  type="date"
                  className="form-control"
                  name="date"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup check>
                <Label id="isAdministrativeLabel">
                  <AvInput id="enrollment-isAdministrative" type="checkbox" className="form-check-input" name="isAdministrative" />
                  <Translate contentKey="enrollmentsApp.enrollment.isAdministrative">Is Administrative</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label for="enrollment-student">
                  <Translate contentKey="enrollmentsApp.enrollment.student">Student</Translate>
                </Label>
                <AvInput id="enrollment-student" type="select" className="form-control" name="studentId">
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
              <AvGroup>
                <Label for="enrollment-classGroup">
                  <Translate contentKey="enrollmentsApp.enrollment.classGroup">Class Group</Translate>
                </Label>
                <AvInput id="enrollment-classGroup" type="select" className="form-control" name="classGroupId">
                  <option value="" key="0" />
                  {classGroups
                    ? classGroups.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/enrollment" replace color="info">
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
  students: storeState.student.entities,
  classGroups: storeState.classGroup.entities,
  enrollmentEntity: storeState.enrollment.entity,
  loading: storeState.enrollment.loading,
  updating: storeState.enrollment.updating,
  updateSuccess: storeState.enrollment.updateSuccess,
});

const mapDispatchToProps = {
  getStudents,
  getClassGroups,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EnrollmentUpdate);

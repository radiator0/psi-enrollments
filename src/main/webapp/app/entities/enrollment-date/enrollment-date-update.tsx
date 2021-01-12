import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ISemester } from 'app/shared/model/semester.model';
import { getEntities as getSemesters } from 'app/entities/semester/semester.reducer';
import { ICourse } from 'app/shared/model/course.model';
import { getEntities as getCourses } from 'app/entities/course/course.reducer';
import { getEntity, updateEntity, createEntity, reset } from './enrollment-date.reducer';
import { IEnrollmentDate } from 'app/shared/model/enrollment-date.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IEnrollmentDateUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EnrollmentDateUpdate = (props: IEnrollmentDateUpdateProps) => {
  const [semesterId, setSemesterId] = useState('0');
  const [courseId, setCourseId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { enrollmentDateEntity, semesters, courses, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/enrollment-date');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getSemesters();
    props.getCourses();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...enrollmentDateEntity,
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
          <h2 id="enrollmentsApp.enrollmentDate.home.createOrEditLabel">
            <Translate contentKey="enrollmentsApp.enrollmentDate.home.createOrEditLabel">Create or edit a EnrollmentDate</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : enrollmentDateEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="enrollment-date-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="enrollment-date-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="enrollment-date-name">
                  <Translate contentKey="enrollmentsApp.enrollmentDate.name">Name</Translate>
                </Label>
                <AvField
                  id="enrollment-date-name"
                  type="text"
                  name="name"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup check>
                <Label id="isPreEnrollmentLabel">
                  <AvInput id="enrollment-date-isPreEnrollment" type="checkbox" className="form-check-input" name="isPreEnrollment" />
                  <Translate contentKey="enrollmentsApp.enrollmentDate.isPreEnrollment">Is Pre Enrollment</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="startDateLabel" for="enrollment-date-startDate">
                  <Translate contentKey="enrollmentsApp.enrollmentDate.startDate">Start Date</Translate>
                </Label>
                <AvField
                  id="enrollment-date-startDate"
                  type="date"
                  className="form-control"
                  name="startDate"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="endDateLabel" for="enrollment-date-endDate">
                  <Translate contentKey="enrollmentsApp.enrollmentDate.endDate">End Date</Translate>
                </Label>
                <AvField
                  id="enrollment-date-endDate"
                  type="date"
                  className="form-control"
                  name="endDate"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label for="enrollment-date-semester">
                  <Translate contentKey="enrollmentsApp.enrollmentDate.semester">Semester</Translate>
                </Label>
                <AvInput id="enrollment-date-semester" type="select" className="form-control" name="semesterId">
                  <option value="" key="0" />
                  {semesters
                    ? semesters.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="enrollment-date-course">
                  <Translate contentKey="enrollmentsApp.enrollmentDate.course">Course</Translate>
                </Label>
                <AvInput id="enrollment-date-course" type="select" className="form-control" name="courseId">
                  <option value="" key="0" />
                  {courses
                    ? courses.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/enrollment-date" replace color="info">
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
  semesters: storeState.semester.entities,
  courses: storeState.course.entities,
  enrollmentDateEntity: storeState.enrollmentDate.entity,
  loading: storeState.enrollmentDate.loading,
  updating: storeState.enrollmentDate.updating,
  updateSuccess: storeState.enrollmentDate.updateSuccess,
});

const mapDispatchToProps = {
  getSemesters,
  getCourses,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EnrollmentDateUpdate);

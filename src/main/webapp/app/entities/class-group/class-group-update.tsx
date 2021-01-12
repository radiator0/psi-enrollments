import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ICourse } from 'app/shared/model/course.model';
import { getEntities as getCourses } from 'app/entities/course/course.reducer';
import { ILecturer } from 'app/shared/model/lecturer.model';
import { getEntities as getLecturers } from 'app/entities/lecturer/lecturer.reducer';
import { getEntity, updateEntity, createEntity, reset } from './class-group.reducer';
import { IClassGroup } from 'app/shared/model/class-group.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IClassGroupUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ClassGroupUpdate = (props: IClassGroupUpdateProps) => {
  const [courseId, setCourseId] = useState('0');
  const [lecturerId, setLecturerId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { classGroupEntity, courses, lecturers, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/class-group' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getCourses();
    props.getLecturers();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...classGroupEntity,
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
          <h2 id="enrollmentsApp.classGroup.home.createOrEditLabel">
            <Translate contentKey="enrollmentsApp.classGroup.home.createOrEditLabel">Create or edit a ClassGroup</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : classGroupEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="class-group-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="class-group-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="codeLabel" for="class-group-code">
                  <Translate contentKey="enrollmentsApp.classGroup.code">Code</Translate>
                </Label>
                <AvField
                  id="class-group-code"
                  type="text"
                  name="code"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup check>
                <Label id="isEnrollmentAboveLimitAllowedLabel">
                  <AvInput
                    id="class-group-isEnrollmentAboveLimitAllowed"
                    type="checkbox"
                    className="form-check-input"
                    name="isEnrollmentAboveLimitAllowed"
                  />
                  <Translate contentKey="enrollmentsApp.classGroup.isEnrollmentAboveLimitAllowed">
                    Is Enrollment Above Limit Allowed
                  </Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="peopleLimitLabel" for="class-group-peopleLimit">
                  <Translate contentKey="enrollmentsApp.classGroup.peopleLimit">People Limit</Translate>
                </Label>
                <AvField
                  id="class-group-peopleLimit"
                  type="string"
                  className="form-control"
                  name="peopleLimit"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    min: { value: 0, errorMessage: translate('entity.validation.min', { min: 0 }) },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="enrolledCountLabel" for="class-group-enrolledCount">
                  <Translate contentKey="enrollmentsApp.classGroup.enrolledCount">Enrolled Count</Translate>
                </Label>
                <AvField
                  id="class-group-enrolledCount"
                  type="string"
                  className="form-control"
                  name="enrolledCount"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    min: { value: 0, errorMessage: translate('entity.validation.min', { min: 0 }) },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup check>
                <Label id="isFullLabel">
                  <AvInput id="class-group-isFull" type="checkbox" className="form-check-input" name="isFull" />
                  <Translate contentKey="enrollmentsApp.classGroup.isFull">Is Full</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label for="class-group-course">
                  <Translate contentKey="enrollmentsApp.classGroup.course">Course</Translate>
                </Label>
                <AvInput id="class-group-course" type="select" className="form-control" name="courseId">
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
              <AvGroup>
                <Label for="class-group-lecturer">
                  <Translate contentKey="enrollmentsApp.classGroup.lecturer">Lecturer</Translate>
                </Label>
                <AvInput id="class-group-lecturer" type="select" className="form-control" name="lecturerId">
                  <option value="" key="0" />
                  {lecturers
                    ? lecturers.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/class-group" replace color="info">
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
  courses: storeState.course.entities,
  lecturers: storeState.lecturer.entities,
  classGroupEntity: storeState.classGroup.entity,
  loading: storeState.classGroup.loading,
  updating: storeState.classGroup.updating,
  updateSuccess: storeState.classGroup.updateSuccess,
});

const mapDispatchToProps = {
  getCourses,
  getLecturers,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ClassGroupUpdate);

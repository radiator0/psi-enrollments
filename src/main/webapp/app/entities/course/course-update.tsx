import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ISpecialty } from 'app/shared/model/specialty.model';
import { getEntities as getSpecialties } from 'app/entities/specialty/specialty.reducer';
import { ICourseUnit } from 'app/shared/model/course-unit.model';
import { getEntities as getCourseUnits } from 'app/entities/course-unit/course-unit.reducer';
import { getEntity, updateEntity, createEntity, reset } from './course.reducer';
import { ICourse } from 'app/shared/model/course.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ICourseUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CourseUpdate = (props: ICourseUpdateProps) => {
  const [idsspecialty, setIdsspecialty] = useState([]);
  const [courseUnitId, setCourseUnitId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { courseEntity, specialties, courseUnits, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/course');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getSpecialties();
    props.getCourseUnits();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...courseEntity,
        ...values,
        specialties: mapIdList(values.specialties),
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
          <h2 id="enrollmentsApp.course.home.createOrEditLabel">
            <Translate contentKey="enrollmentsApp.course.home.createOrEditLabel">Create or edit a Course</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : courseEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="course-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="course-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="course-name">
                  <Translate contentKey="enrollmentsApp.course.name">Name</Translate>
                </Label>
                <AvField
                  id="course-name"
                  type="text"
                  name="name"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="shortNameLabel" for="course-shortName">
                  <Translate contentKey="enrollmentsApp.course.shortName">Short Name</Translate>
                </Label>
                <AvField id="course-shortName" type="text" name="shortName" />
              </AvGroup>
              <AvGroup>
                <Label id="codeLabel" for="course-code">
                  <Translate contentKey="enrollmentsApp.course.code">Code</Translate>
                </Label>
                <AvField
                  id="course-code"
                  type="text"
                  name="code"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="ectsLabel" for="course-ects">
                  <Translate contentKey="enrollmentsApp.course.ects">Ects</Translate>
                </Label>
                <AvField
                  id="course-ects"
                  type="string"
                  className="form-control"
                  name="ects"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    min: { value: 0, errorMessage: translate('entity.validation.min', { min: 0 }) },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="formLabel" for="course-form">
                  <Translate contentKey="enrollmentsApp.course.form">Form</Translate>
                </Label>
                <AvInput
                  id="course-form"
                  type="select"
                  className="form-control"
                  name="form"
                  value={(!isNew && courseEntity.form) || 'Project'}
                >
                  <option value="Project">{translate('enrollmentsApp.ClassType.Project')}</option>
                  <option value="Laboratory">{translate('enrollmentsApp.ClassType.Laboratory')}</option>
                  <option value="Exercises">{translate('enrollmentsApp.ClassType.Exercises')}</option>
                  <option value="Seminar">{translate('enrollmentsApp.ClassType.Seminar')}</option>
                  <option value="Lecture">{translate('enrollmentsApp.ClassType.Lecture')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="course-specialty">
                  <Translate contentKey="enrollmentsApp.course.specialty">Specialty</Translate>
                </Label>
                <AvInput
                  id="course-specialty"
                  type="select"
                  multiple
                  className="form-control"
                  name="specialties"
                  value={courseEntity.specialties && courseEntity.specialties.map(e => e.id)}
                >
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
                <Label for="course-courseUnit">
                  <Translate contentKey="enrollmentsApp.course.courseUnit">Course Unit</Translate>
                </Label>
                <AvInput id="course-courseUnit" type="select" className="form-control" name="courseUnitId">
                  <option value="" key="0" />
                  {courseUnits
                    ? courseUnits.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/course" replace color="info">
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
  specialties: storeState.specialty.entities,
  courseUnits: storeState.courseUnit.entities,
  courseEntity: storeState.course.entity,
  loading: storeState.course.loading,
  updating: storeState.course.updating,
  updateSuccess: storeState.course.updateSuccess,
});

const mapDispatchToProps = {
  getSpecialties,
  getCourseUnits,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CourseUpdate);

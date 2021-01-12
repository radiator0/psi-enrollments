import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ISelectableModule } from 'app/shared/model/selectable-module.model';
import { getEntities as getSelectableModules } from 'app/entities/selectable-module/selectable-module.reducer';
import { getEntity, updateEntity, createEntity, reset } from './course-unit.reducer';
import { ICourseUnit } from 'app/shared/model/course-unit.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ICourseUnitUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CourseUnitUpdate = (props: ICourseUnitUpdateProps) => {
  const [selectableModuleId, setSelectableModuleId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { courseUnitEntity, selectableModules, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/course-unit');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getSelectableModules();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...courseUnitEntity,
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
          <h2 id="enrollmentsApp.courseUnit.home.createOrEditLabel">
            <Translate contentKey="enrollmentsApp.courseUnit.home.createOrEditLabel">Create or edit a CourseUnit</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : courseUnitEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="course-unit-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="course-unit-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="codeLabel" for="course-unit-code">
                  <Translate contentKey="enrollmentsApp.courseUnit.code">Code</Translate>
                </Label>
                <AvField id="course-unit-code" type="text" name="code" />
              </AvGroup>
              <AvGroup>
                <Label id="ectsLabel" for="course-unit-ects">
                  <Translate contentKey="enrollmentsApp.courseUnit.ects">Ects</Translate>
                </Label>
                <AvField
                  id="course-unit-ects"
                  type="string"
                  className="form-control"
                  name="ects"
                  validate={{
                    min: { value: 0, errorMessage: translate('entity.validation.min', { min: 0 }) },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup check>
                <Label id="isGroupOfCoursesLabel">
                  <AvInput id="course-unit-isGroupOfCourses" type="checkbox" className="form-check-input" name="isGroupOfCourses" />
                  <Translate contentKey="enrollmentsApp.courseUnit.isGroupOfCourses">Is Group Of Courses</Translate>
                </Label>
              </AvGroup>
              <AvGroup check>
                <Label id="isStreamLabel">
                  <AvInput id="course-unit-isStream" type="checkbox" className="form-check-input" name="isStream" />
                  <Translate contentKey="enrollmentsApp.courseUnit.isStream">Is Stream</Translate>
                </Label>
              </AvGroup>
              <AvGroup check>
                <Label id="isSelectableLabel">
                  <AvInput id="course-unit-isSelectable" type="checkbox" className="form-check-input" name="isSelectable" />
                  <Translate contentKey="enrollmentsApp.courseUnit.isSelectable">Is Selectable</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label for="course-unit-selectableModule">
                  <Translate contentKey="enrollmentsApp.courseUnit.selectableModule">Selectable Module</Translate>
                </Label>
                <AvInput id="course-unit-selectableModule" type="select" className="form-control" name="selectableModuleId">
                  <option value="" key="0" />
                  {selectableModules
                    ? selectableModules.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/course-unit" replace color="info">
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
  selectableModules: storeState.selectableModule.entities,
  courseUnitEntity: storeState.courseUnit.entity,
  loading: storeState.courseUnit.loading,
  updating: storeState.courseUnit.updating,
  updateSuccess: storeState.courseUnit.updateSuccess,
});

const mapDispatchToProps = {
  getSelectableModules,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CourseUnitUpdate);

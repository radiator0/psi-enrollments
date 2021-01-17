import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IStudyProgram } from 'app/shared/model/study-program.model';
import { getEntities as getStudyPrograms } from 'app/entities/study-program/study-program.reducer';
import { getEntity, updateEntity, createEntity, reset } from './field-of-study.reducer';
import { IFieldOfStudy } from 'app/shared/model/field-of-study.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IFieldOfStudyUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const FieldOfStudyUpdate = (props: IFieldOfStudyUpdateProps) => {
  const [studyProgramId, setStudyProgramId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { fieldOfStudyEntity, studyPrograms, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/field-of-study');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getStudyPrograms();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...fieldOfStudyEntity,
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
          <h2 id="enrollmentsApp.fieldOfStudy.home.createOrEditLabel">
            <Translate contentKey="enrollmentsApp.fieldOfStudy.home.createOrEditLabel">Create or edit a FieldOfStudy</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : fieldOfStudyEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="field-of-study-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="field-of-study-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="field-of-study-name">
                  <Translate contentKey="enrollmentsApp.fieldOfStudy.name">Name</Translate>
                </Label>
                <AvField
                  id="field-of-study-name"
                  type="text"
                  name="name"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="uniqueNameLabel" for="field-of-study-uniqueName">
                  <Translate contentKey="enrollmentsApp.fieldOfStudy.uniqueName">Unique Name</Translate>
                </Label>
                <AvField
                  id="field-of-study-uniqueName"
                  type="text"
                  name="uniqueName"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label for="field-of-study-studyProgram">
                  <Translate contentKey="enrollmentsApp.fieldOfStudy.studyProgram">Study Program</Translate>
                </Label>
                <AvInput id="field-of-study-studyProgram" type="select" className="form-control" name="studyProgramId" required>
                  {studyPrograms
                    ? studyPrograms.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
                <AvFeedback>
                  <Translate contentKey="entity.validation.required">This field is required.</Translate>
                </AvFeedback>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/field-of-study" replace color="info">
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
  studyPrograms: storeState.studyProgram.entities,
  fieldOfStudyEntity: storeState.fieldOfStudy.entity,
  loading: storeState.fieldOfStudy.loading,
  updating: storeState.fieldOfStudy.updating,
  updateSuccess: storeState.fieldOfStudy.updateSuccess,
});

const mapDispatchToProps = {
  getStudyPrograms,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(FieldOfStudyUpdate);

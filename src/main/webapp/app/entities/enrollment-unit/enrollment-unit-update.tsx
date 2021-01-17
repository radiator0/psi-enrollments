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
import { getEntity, updateEntity, createEntity, reset } from './enrollment-unit.reducer';
import { IEnrollmentUnit } from 'app/shared/model/enrollment-unit.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IEnrollmentUnitUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EnrollmentUnitUpdate = (props: IEnrollmentUnitUpdateProps) => {
  const [enrollmentDateId, setEnrollmentDateId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { enrollmentUnitEntity, enrollmentDates, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/enrollment-unit');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getEnrollmentDates();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...enrollmentUnitEntity,
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
          <h2 id="enrollmentsApp.enrollmentUnit.home.createOrEditLabel">
            <Translate contentKey="enrollmentsApp.enrollmentUnit.home.createOrEditLabel">Create or edit a EnrollmentUnit</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : enrollmentUnitEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="enrollment-unit-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="enrollment-unit-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="startDateLabel" for="enrollment-unit-startDate">
                  <Translate contentKey="enrollmentsApp.enrollmentUnit.startDate">Start Date</Translate>
                </Label>
                <AvField
                  id="enrollment-unit-startDate"
                  type="date"
                  className="form-control"
                  name="startDate"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="endDateLabel" for="enrollment-unit-endDate">
                  <Translate contentKey="enrollmentsApp.enrollmentUnit.endDate">End Date</Translate>
                </Label>
                <AvField
                  id="enrollment-unit-endDate"
                  type="date"
                  className="form-control"
                  name="endDate"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label for="enrollment-unit-enrollmentDate">
                  <Translate contentKey="enrollmentsApp.enrollmentUnit.enrollmentDate">Enrollment Date</Translate>
                </Label>
                <AvInput id="enrollment-unit-enrollmentDate" type="select" className="form-control" name="enrollmentDateId" required>
                  {enrollmentDates
                    ? enrollmentDates.map(otherEntity => (
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
              <Button tag={Link} id="cancel-save" to="/enrollment-unit" replace color="info">
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
  enrollmentUnitEntity: storeState.enrollmentUnit.entity,
  loading: storeState.enrollmentUnit.loading,
  updating: storeState.enrollmentUnit.updating,
  updateSuccess: storeState.enrollmentUnit.updateSuccess,
});

const mapDispatchToProps = {
  getEnrollmentDates,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EnrollmentUnitUpdate);

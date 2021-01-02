import React from 'react';
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
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IEnrollmentUnitUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IEnrollmentUnitUpdateState {
  isNew: boolean;
  enrollmentDateId: string;
}

export class EnrollmentUnitUpdate extends React.Component<IEnrollmentUnitUpdateProps, IEnrollmentUnitUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      enrollmentDateId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getEnrollmentDates();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { enrollmentUnitEntity } = this.props;
      const entity = {
        ...enrollmentUnitEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/enrollment-unit');
  };

  render() {
    const { enrollmentUnitEntity, enrollmentDates, loading, updating } = this.props;
    const { isNew } = this.state;

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
              <AvForm model={isNew ? {} : enrollmentUnitEntity} onSubmit={this.saveEntity}>
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
                      required: { value: true, errorMessage: translate('entity.validation.required') }
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
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label for="enrollment-unit-enrollmentDate">
                    <Translate contentKey="enrollmentsApp.enrollmentUnit.enrollmentDate">Enrollment Date</Translate>
                  </Label>
                  <AvInput id="enrollment-unit-enrollmentDate" type="select" className="form-control" name="enrollmentDateId">
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
                <Button tag={Link} id="cancel-save" to="/entity/enrollment-unit" replace color="info">
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
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  enrollmentDates: storeState.enrollmentDate.entities,
  enrollmentUnitEntity: storeState.enrollmentUnit.entity,
  loading: storeState.enrollmentUnit.loading,
  updating: storeState.enrollmentUnit.updating,
  updateSuccess: storeState.enrollmentUnit.updateSuccess
});

const mapDispatchToProps = {
  getEnrollmentDates,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(EnrollmentUnitUpdate);

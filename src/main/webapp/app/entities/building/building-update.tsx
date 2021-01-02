import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './building.reducer';
import { IBuilding } from 'app/shared/model/building.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IBuildingUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IBuildingUpdateState {
  isNew: boolean;
}

export class BuildingUpdate extends React.Component<IBuildingUpdateProps, IBuildingUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
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
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { buildingEntity } = this.props;
      const entity = {
        ...buildingEntity,
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
    this.props.history.push('/entity/building');
  };

  render() {
    const { buildingEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="enrollmentsApp.building.home.createOrEditLabel">
              <Translate contentKey="enrollmentsApp.building.home.createOrEditLabel">Create or edit a Building</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : buildingEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="building-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="building-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel" for="building-name">
                    <Translate contentKey="enrollmentsApp.building.name">Name</Translate>
                  </Label>
                  <AvField
                    id="building-name"
                    type="text"
                    name="name"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="placeLabel" for="building-place">
                    <Translate contentKey="enrollmentsApp.building.place">Place</Translate>
                  </Label>
                  <AvField
                    id="building-place"
                    type="text"
                    name="place"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="postcodeLabel" for="building-postcode">
                    <Translate contentKey="enrollmentsApp.building.postcode">Postcode</Translate>
                  </Label>
                  <AvField
                    id="building-postcode"
                    type="text"
                    name="postcode"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="streetLabel" for="building-street">
                    <Translate contentKey="enrollmentsApp.building.street">Street</Translate>
                  </Label>
                  <AvField
                    id="building-street"
                    type="text"
                    name="street"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="numberLabel" for="building-number">
                    <Translate contentKey="enrollmentsApp.building.number">Number</Translate>
                  </Label>
                  <AvField
                    id="building-number"
                    type="text"
                    name="number"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="longitudeLabel" for="building-longitude">
                    <Translate contentKey="enrollmentsApp.building.longitude">Longitude</Translate>
                  </Label>
                  <AvField
                    id="building-longitude"
                    type="string"
                    className="form-control"
                    name="longitude"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') },
                      number: { value: true, errorMessage: translate('entity.validation.number') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="latitudeLabel" for="building-latitude">
                    <Translate contentKey="enrollmentsApp.building.latitude">Latitude</Translate>
                  </Label>
                  <AvField
                    id="building-latitude"
                    type="string"
                    className="form-control"
                    name="latitude"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') },
                      number: { value: true, errorMessage: translate('entity.validation.number') }
                    }}
                  />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/building" replace color="info">
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
  buildingEntity: storeState.building.entity,
  loading: storeState.building.loading,
  updating: storeState.building.updating,
  updateSuccess: storeState.building.updateSuccess
});

const mapDispatchToProps = {
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
)(BuildingUpdate);

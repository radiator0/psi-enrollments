import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './building.reducer';
import { IBuilding } from 'app/shared/model/building.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IBuildingDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class BuildingDetail extends React.Component<IBuildingDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { buildingEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="enrollmentsApp.building.detail.title">Building</Translate> [<b>{buildingEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">
                <Translate contentKey="enrollmentsApp.building.name">Name</Translate>
              </span>
            </dt>
            <dd>{buildingEntity.name}</dd>
            <dt>
              <span id="place">
                <Translate contentKey="enrollmentsApp.building.place">Place</Translate>
              </span>
            </dt>
            <dd>{buildingEntity.place}</dd>
            <dt>
              <span id="postcode">
                <Translate contentKey="enrollmentsApp.building.postcode">Postcode</Translate>
              </span>
            </dt>
            <dd>{buildingEntity.postcode}</dd>
            <dt>
              <span id="street">
                <Translate contentKey="enrollmentsApp.building.street">Street</Translate>
              </span>
            </dt>
            <dd>{buildingEntity.street}</dd>
            <dt>
              <span id="number">
                <Translate contentKey="enrollmentsApp.building.number">Number</Translate>
              </span>
            </dt>
            <dd>{buildingEntity.number}</dd>
            <dt>
              <span id="longitude">
                <Translate contentKey="enrollmentsApp.building.longitude">Longitude</Translate>
              </span>
            </dt>
            <dd>{buildingEntity.longitude}</dd>
            <dt>
              <span id="latitude">
                <Translate contentKey="enrollmentsApp.building.latitude">Latitude</Translate>
              </span>
            </dt>
            <dd>{buildingEntity.latitude}</dd>
          </dl>
          <Button tag={Link} to="/entity/building" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/building/${buildingEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ building }: IRootState) => ({
  buildingEntity: building.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(BuildingDetail);

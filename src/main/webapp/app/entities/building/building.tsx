import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './building.reducer';
import { IBuilding } from 'app/shared/model/building.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IBuildingProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class Building extends React.Component<IBuildingProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { buildingList, match } = this.props;
    return (
      <div>
        <h2 id="building-heading">
          <Translate contentKey="enrollmentsApp.building.home.title">Buildings</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="enrollmentsApp.building.home.createLabel">Create a new Building</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          {buildingList && buildingList.length > 0 ? (
            <Table responsive aria-describedby="building-heading">
              <thead>
                <tr>
                  <th>
                    <Translate contentKey="global.field.id">ID</Translate>
                  </th>
                  <th>
                    <Translate contentKey="enrollmentsApp.building.name">Name</Translate>
                  </th>
                  <th>
                    <Translate contentKey="enrollmentsApp.building.place">Place</Translate>
                  </th>
                  <th>
                    <Translate contentKey="enrollmentsApp.building.postcode">Postcode</Translate>
                  </th>
                  <th>
                    <Translate contentKey="enrollmentsApp.building.street">Street</Translate>
                  </th>
                  <th>
                    <Translate contentKey="enrollmentsApp.building.number">Number</Translate>
                  </th>
                  <th>
                    <Translate contentKey="enrollmentsApp.building.longitude">Longitude</Translate>
                  </th>
                  <th>
                    <Translate contentKey="enrollmentsApp.building.latitude">Latitude</Translate>
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {buildingList.map((building, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${building.id}`} color="link" size="sm">
                        {building.id}
                      </Button>
                    </td>
                    <td>{building.name}</td>
                    <td>{building.place}</td>
                    <td>{building.postcode}</td>
                    <td>{building.street}</td>
                    <td>{building.number}</td>
                    <td>{building.longitude}</td>
                    <td>{building.latitude}</td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${building.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${building.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${building.id}/delete`} color="danger" size="sm">
                          <FontAwesomeIcon icon="trash" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.delete">Delete</Translate>
                          </span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            <div className="alert alert-warning">
              <Translate contentKey="enrollmentsApp.building.home.notFound">No Buildings found</Translate>
            </div>
          )}
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ building }: IRootState) => ({
  buildingList: building.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Building);

import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './class-unit.reducer';
import { IClassUnit } from 'app/shared/model/class-unit.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IClassUnitProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class ClassUnit extends React.Component<IClassUnitProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { classUnitList, match } = this.props;
    return (
      <div>
        <h2 id="class-unit-heading">
          <Translate contentKey="enrollmentsApp.classUnit.home.title">Class Units</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="enrollmentsApp.classUnit.home.createLabel">Create a new Class Unit</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          {classUnitList && classUnitList.length > 0 ? (
            <Table responsive aria-describedby="class-unit-heading">
              <thead>
                <tr>
                  <th>
                    <Translate contentKey="global.field.id">ID</Translate>
                  </th>
                  <th>
                    <Translate contentKey="enrollmentsApp.classUnit.day">Day</Translate>
                  </th>
                  <th>
                    <Translate contentKey="enrollmentsApp.classUnit.startTime">Start Time</Translate>
                  </th>
                  <th>
                    <Translate contentKey="enrollmentsApp.classUnit.endTime">End Time</Translate>
                  </th>
                  <th>
                    <Translate contentKey="enrollmentsApp.classUnit.classGroup">Class Group</Translate>
                  </th>
                  <th>
                    <Translate contentKey="enrollmentsApp.classUnit.room">Room</Translate>
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {classUnitList.map((classUnit, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${classUnit.id}`} color="link" size="sm">
                        {classUnit.id}
                      </Button>
                    </td>
                    <td>
                      <TextFormat type="date" value={classUnit.day} format={APP_LOCAL_DATE_FORMAT} />
                    </td>
                    <td>
                      <TextFormat type="date" value={classUnit.startTime} format={APP_DATE_FORMAT} />
                    </td>
                    <td>
                      <TextFormat type="date" value={classUnit.endTime} format={APP_DATE_FORMAT} />
                    </td>
                    <td>
                      {classUnit.classGroupId ? <Link to={`class-group/${classUnit.classGroupId}`}>{classUnit.classGroupId}</Link> : ''}
                    </td>
                    <td>{classUnit.roomId ? <Link to={`room/${classUnit.roomId}`}>{classUnit.roomId}</Link> : ''}</td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${classUnit.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${classUnit.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${classUnit.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="enrollmentsApp.classUnit.home.notFound">No Class Units found</Translate>
            </div>
          )}
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ classUnit }: IRootState) => ({
  classUnitList: classUnit.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ClassUnit);

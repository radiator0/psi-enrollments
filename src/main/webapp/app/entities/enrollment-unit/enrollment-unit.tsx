import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './enrollment-unit.reducer';
import { IEnrollmentUnit } from 'app/shared/model/enrollment-unit.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEnrollmentUnitProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class EnrollmentUnit extends React.Component<IEnrollmentUnitProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { enrollmentUnitList, match } = this.props;
    return (
      <div>
        <h2 id="enrollment-unit-heading">
          <Translate contentKey="enrollmentsApp.enrollmentUnit.home.title">Enrollment Units</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="enrollmentsApp.enrollmentUnit.home.createLabel">Create a new Enrollment Unit</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          {enrollmentUnitList && enrollmentUnitList.length > 0 ? (
            <Table responsive aria-describedby="enrollment-unit-heading">
              <thead>
                <tr>
                  <th>
                    <Translate contentKey="global.field.id">ID</Translate>
                  </th>
                  <th>
                    <Translate contentKey="enrollmentsApp.enrollmentUnit.startDate">Start Date</Translate>
                  </th>
                  <th>
                    <Translate contentKey="enrollmentsApp.enrollmentUnit.endDate">End Date</Translate>
                  </th>
                  <th>
                    <Translate contentKey="enrollmentsApp.enrollmentUnit.enrollmentDate">Enrollment Date</Translate>
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {enrollmentUnitList.map((enrollmentUnit, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${enrollmentUnit.id}`} color="link" size="sm">
                        {enrollmentUnit.id}
                      </Button>
                    </td>
                    <td>
                      <TextFormat type="date" value={enrollmentUnit.startDate} format={APP_LOCAL_DATE_FORMAT} />
                    </td>
                    <td>
                      <TextFormat type="date" value={enrollmentUnit.endDate} format={APP_LOCAL_DATE_FORMAT} />
                    </td>
                    <td>
                      {enrollmentUnit.enrollmentDateId ? (
                        <Link to={`enrollment-date/${enrollmentUnit.enrollmentDateId}`}>{enrollmentUnit.enrollmentDateId}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${enrollmentUnit.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${enrollmentUnit.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${enrollmentUnit.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="enrollmentsApp.enrollmentUnit.home.notFound">No Enrollment Units found</Translate>
            </div>
          )}
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ enrollmentUnit }: IRootState) => ({
  enrollmentUnitList: enrollmentUnit.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(EnrollmentUnit);

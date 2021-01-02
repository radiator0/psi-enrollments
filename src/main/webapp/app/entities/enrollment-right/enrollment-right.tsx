import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './enrollment-right.reducer';
import { IEnrollmentRight } from 'app/shared/model/enrollment-right.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEnrollmentRightProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class EnrollmentRight extends React.Component<IEnrollmentRightProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { enrollmentRightList, match } = this.props;
    return (
      <div>
        <h2 id="enrollment-right-heading">
          <Translate contentKey="enrollmentsApp.enrollmentRight.home.title">Enrollment Rights</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="enrollmentsApp.enrollmentRight.home.createLabel">Create a new Enrollment Right</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          {enrollmentRightList && enrollmentRightList.length > 0 ? (
            <Table responsive aria-describedby="enrollment-right-heading">
              <thead>
                <tr>
                  <th>
                    <Translate contentKey="global.field.id">ID</Translate>
                  </th>
                  <th>
                    <Translate contentKey="enrollmentsApp.enrollmentRight.startDate">Start Date</Translate>
                  </th>
                  <th>
                    <Translate contentKey="enrollmentsApp.enrollmentRight.enrollmentDate">Enrollment Date</Translate>
                  </th>
                  <th>
                    <Translate contentKey="enrollmentsApp.enrollmentRight.specialty">Specialty</Translate>
                  </th>
                  <th>
                    <Translate contentKey="enrollmentsApp.enrollmentRight.student">Student</Translate>
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {enrollmentRightList.map((enrollmentRight, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${enrollmentRight.id}`} color="link" size="sm">
                        {enrollmentRight.id}
                      </Button>
                    </td>
                    <td>
                      <TextFormat type="date" value={enrollmentRight.startDate} format={APP_LOCAL_DATE_FORMAT} />
                    </td>
                    <td>
                      {enrollmentRight.enrollmentDateId ? (
                        <Link to={`enrollment-date/${enrollmentRight.enrollmentDateId}`}>{enrollmentRight.enrollmentDateId}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {enrollmentRight.specialtyId ? (
                        <Link to={`specialty/${enrollmentRight.specialtyId}`}>{enrollmentRight.specialtyId}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {enrollmentRight.studentId ? (
                        <Link to={`student/${enrollmentRight.studentId}`}>{enrollmentRight.studentId}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${enrollmentRight.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${enrollmentRight.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${enrollmentRight.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="enrollmentsApp.enrollmentRight.home.notFound">No Enrollment Rights found</Translate>
            </div>
          )}
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ enrollmentRight }: IRootState) => ({
  enrollmentRightList: enrollmentRight.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(EnrollmentRight);

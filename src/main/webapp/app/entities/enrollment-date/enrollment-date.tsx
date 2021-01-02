import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './enrollment-date.reducer';
import { IEnrollmentDate } from 'app/shared/model/enrollment-date.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEnrollmentDateProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class EnrollmentDate extends React.Component<IEnrollmentDateProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { enrollmentDateList, match } = this.props;
    return (
      <div>
        <h2 id="enrollment-date-heading">
          <Translate contentKey="enrollmentsApp.enrollmentDate.home.title">Enrollment Dates</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="enrollmentsApp.enrollmentDate.home.createLabel">Create a new Enrollment Date</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          {enrollmentDateList && enrollmentDateList.length > 0 ? (
            <Table responsive aria-describedby="enrollment-date-heading">
              <thead>
                <tr>
                  <th>
                    <Translate contentKey="global.field.id">ID</Translate>
                  </th>
                  <th>
                    <Translate contentKey="enrollmentsApp.enrollmentDate.name">Name</Translate>
                  </th>
                  <th>
                    <Translate contentKey="enrollmentsApp.enrollmentDate.isPreEnrollment">Is Pre Enrollment</Translate>
                  </th>
                  <th>
                    <Translate contentKey="enrollmentsApp.enrollmentDate.startDate">Start Date</Translate>
                  </th>
                  <th>
                    <Translate contentKey="enrollmentsApp.enrollmentDate.endDate">End Date</Translate>
                  </th>
                  <th>
                    <Translate contentKey="enrollmentsApp.enrollmentDate.semester">Semester</Translate>
                  </th>
                  <th>
                    <Translate contentKey="enrollmentsApp.enrollmentDate.course">Course</Translate>
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {enrollmentDateList.map((enrollmentDate, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${enrollmentDate.id}`} color="link" size="sm">
                        {enrollmentDate.id}
                      </Button>
                    </td>
                    <td>{enrollmentDate.name}</td>
                    <td>{enrollmentDate.isPreEnrollment ? 'true' : 'false'}</td>
                    <td>
                      <TextFormat type="date" value={enrollmentDate.startDate} format={APP_LOCAL_DATE_FORMAT} />
                    </td>
                    <td>
                      <TextFormat type="date" value={enrollmentDate.endDate} format={APP_LOCAL_DATE_FORMAT} />
                    </td>
                    <td>
                      {enrollmentDate.semesterId ? (
                        <Link to={`semester/${enrollmentDate.semesterId}`}>{enrollmentDate.semesterId}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {enrollmentDate.courseId ? <Link to={`course/${enrollmentDate.courseId}`}>{enrollmentDate.courseId}</Link> : ''}
                    </td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${enrollmentDate.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${enrollmentDate.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${enrollmentDate.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="enrollmentsApp.enrollmentDate.home.notFound">No Enrollment Dates found</Translate>
            </div>
          )}
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ enrollmentDate }: IRootState) => ({
  enrollmentDateList: enrollmentDate.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(EnrollmentDate);

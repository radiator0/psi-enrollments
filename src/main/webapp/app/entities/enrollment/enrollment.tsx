import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './enrollment.reducer';
import { IEnrollment } from 'app/shared/model/enrollment.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEnrollmentProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class Enrollment extends React.Component<IEnrollmentProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { enrollmentList, match } = this.props;
    return (
      <div>
        <h2 id="enrollment-heading">
          <Translate contentKey="enrollmentsApp.enrollment.home.title">Enrollments</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="enrollmentsApp.enrollment.home.createLabel">Create a new Enrollment</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          {enrollmentList && enrollmentList.length > 0 ? (
            <Table responsive aria-describedby="enrollment-heading">
              <thead>
                <tr>
                  <th>
                    <Translate contentKey="global.field.id">ID</Translate>
                  </th>
                  <th>
                    <Translate contentKey="enrollmentsApp.enrollment.date">Date</Translate>
                  </th>
                  <th>
                    <Translate contentKey="enrollmentsApp.enrollment.isAdministrative">Is Administrative</Translate>
                  </th>
                  <th>
                    <Translate contentKey="enrollmentsApp.enrollment.student">Student</Translate>
                  </th>
                  <th>
                    <Translate contentKey="enrollmentsApp.enrollment.classGroup">Class Group</Translate>
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {enrollmentList.map((enrollment, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${enrollment.id}`} color="link" size="sm">
                        {enrollment.id}
                      </Button>
                    </td>
                    <td>
                      <TextFormat type="date" value={enrollment.date} format={APP_LOCAL_DATE_FORMAT} />
                    </td>
                    <td>{enrollment.isAdministrative ? 'true' : 'false'}</td>
                    <td>{enrollment.studentId ? <Link to={`student/${enrollment.studentId}`}>{enrollment.studentId}</Link> : ''}</td>
                    <td>
                      {enrollment.classGroupId ? <Link to={`class-group/${enrollment.classGroupId}`}>{enrollment.classGroupId}</Link> : ''}
                    </td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${enrollment.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${enrollment.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${enrollment.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="enrollmentsApp.enrollment.home.notFound">No Enrollments found</Translate>
            </div>
          )}
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ enrollment }: IRootState) => ({
  enrollmentList: enrollment.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Enrollment);

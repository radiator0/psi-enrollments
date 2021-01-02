import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './student.reducer';
import { IStudent } from 'app/shared/model/student.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IStudentProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class Student extends React.Component<IStudentProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { studentList, match } = this.props;
    return (
      <div>
        <h2 id="student-heading">
          <Translate contentKey="enrollmentsApp.student.home.title">Students</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="enrollmentsApp.student.home.createLabel">Create a new Student</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          {studentList && studentList.length > 0 ? (
            <Table responsive aria-describedby="student-heading">
              <thead>
                <tr>
                  <th>
                    <Translate contentKey="global.field.id">ID</Translate>
                  </th>
                  <th>
                    <Translate contentKey="enrollmentsApp.student.firstName">First Name</Translate>
                  </th>
                  <th>
                    <Translate contentKey="enrollmentsApp.student.secondName">Second Name</Translate>
                  </th>
                  <th>
                    <Translate contentKey="enrollmentsApp.student.lastName">Last Name</Translate>
                  </th>
                  <th>
                    <Translate contentKey="enrollmentsApp.student.mail">Mail</Translate>
                  </th>
                  <th>
                    <Translate contentKey="enrollmentsApp.student.title">Title</Translate>
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {studentList.map((student, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${student.id}`} color="link" size="sm">
                        {student.id}
                      </Button>
                    </td>
                    <td>{student.firstName}</td>
                    <td>{student.secondName}</td>
                    <td>{student.lastName}</td>
                    <td>{student.mail}</td>
                    <td>{student.title}</td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${student.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${student.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${student.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="enrollmentsApp.student.home.notFound">No Students found</Translate>
            </div>
          )}
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ student }: IRootState) => ({
  studentList: student.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Student);

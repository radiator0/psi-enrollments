import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './lecturer.reducer';
import { ILecturer } from 'app/shared/model/lecturer.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ILecturerProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Lecturer = (props: ILecturerProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { lecturerList, match, loading } = props;
  return (
    <div>
      <h2 id="lecturer-heading">
        <Translate contentKey="enrollmentsApp.lecturer.home.title">Lecturers</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="enrollmentsApp.lecturer.home.createLabel">Create new Lecturer</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {lecturerList && lecturerList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="enrollmentsApp.lecturer.firstName">First Name</Translate>
                </th>
                <th>
                  <Translate contentKey="enrollmentsApp.lecturer.secondName">Second Name</Translate>
                </th>
                <th>
                  <Translate contentKey="enrollmentsApp.lecturer.lastName">Last Name</Translate>
                </th>
                <th>
                  <Translate contentKey="enrollmentsApp.lecturer.mail">Mail</Translate>
                </th>
                <th>
                  <Translate contentKey="enrollmentsApp.lecturer.title">Title</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {lecturerList.map((lecturer, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${lecturer.id}`} color="link" size="sm">
                      {lecturer.id}
                    </Button>
                  </td>
                  <td>{lecturer.firstName}</td>
                  <td>{lecturer.secondName}</td>
                  <td>{lecturer.lastName}</td>
                  <td>{lecturer.mail}</td>
                  <td>{lecturer.title}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${lecturer.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${lecturer.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${lecturer.id}/delete`} color="danger" size="sm">
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
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="enrollmentsApp.lecturer.home.notFound">No Lecturers found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ lecturer }: IRootState) => ({
  lecturerList: lecturer.entities,
  loading: lecturer.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Lecturer);

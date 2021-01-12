import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './semester.reducer';
import { ISemester } from 'app/shared/model/semester.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ISemesterProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Semester = (props: ISemesterProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { semesterList, match, loading } = props;
  return (
    <div>
      <h2 id="semester-heading">
        <Translate contentKey="enrollmentsApp.semester.home.title">Semesters</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="enrollmentsApp.semester.home.createLabel">Create new Semester</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {semesterList && semesterList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="enrollmentsApp.semester.number">Number</Translate>
                </th>
                <th>
                  <Translate contentKey="enrollmentsApp.semester.semesterType">Semester Type</Translate>
                </th>
                <th>
                  <Translate contentKey="enrollmentsApp.semester.startDate">Start Date</Translate>
                </th>
                <th>
                  <Translate contentKey="enrollmentsApp.semester.fieldOfStudy">Field Of Study</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {semesterList.map((semester, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${semester.id}`} color="link" size="sm">
                      {semester.id}
                    </Button>
                  </td>
                  <td>{semester.number}</td>
                  <td>
                    <Translate contentKey={`enrollmentsApp.SemesterType.${semester.semesterType}`} />
                  </td>
                  <td>
                    {semester.startDate ? <TextFormat type="date" value={semester.startDate} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {semester.fieldOfStudyId ? <Link to={`field-of-study/${semester.fieldOfStudyId}`}>{semester.fieldOfStudyId}</Link> : ''}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${semester.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${semester.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${semester.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="enrollmentsApp.semester.home.notFound">No Semesters found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ semester }: IRootState) => ({
  semesterList: semester.entities,
  loading: semester.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Semester);

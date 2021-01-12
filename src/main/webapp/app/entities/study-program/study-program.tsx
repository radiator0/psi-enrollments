import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './study-program.reducer';
import { IStudyProgram } from 'app/shared/model/study-program.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IStudyProgramProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const StudyProgram = (props: IStudyProgramProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { studyProgramList, match, loading } = props;
  return (
    <div>
      <h2 id="study-program-heading">
        <Translate contentKey="enrollmentsApp.studyProgram.home.title">Study Programs</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="enrollmentsApp.studyProgram.home.createLabel">Create new Study Program</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {studyProgramList && studyProgramList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="enrollmentsApp.studyProgram.startYear">Start Year</Translate>
                </th>
                <th>
                  <Translate contentKey="enrollmentsApp.studyProgram.endYear">End Year</Translate>
                </th>
                <th>
                  <Translate contentKey="enrollmentsApp.studyProgram.startingSemesterType">Starting Semester Type</Translate>
                </th>
                <th>
                  <Translate contentKey="enrollmentsApp.studyProgram.studyType">Study Type</Translate>
                </th>
                <th>
                  <Translate contentKey="enrollmentsApp.studyProgram.studyForm">Study Form</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {studyProgramList.map((studyProgram, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${studyProgram.id}`} color="link" size="sm">
                      {studyProgram.id}
                    </Button>
                  </td>
                  <td>{studyProgram.startYear}</td>
                  <td>{studyProgram.endYear}</td>
                  <td>
                    <Translate contentKey={`enrollmentsApp.SemesterType.${studyProgram.startingSemesterType}`} />
                  </td>
                  <td>
                    <Translate contentKey={`enrollmentsApp.StudyType.${studyProgram.studyType}`} />
                  </td>
                  <td>
                    <Translate contentKey={`enrollmentsApp.StudyForm.${studyProgram.studyForm}`} />
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${studyProgram.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${studyProgram.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${studyProgram.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="enrollmentsApp.studyProgram.home.notFound">No Study Programs found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ studyProgram }: IRootState) => ({
  studyProgramList: studyProgram.entities,
  loading: studyProgram.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(StudyProgram);

import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './course-unit.reducer';
import { ICourseUnit } from 'app/shared/model/course-unit.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICourseUnitProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const CourseUnit = (props: ICourseUnitProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { courseUnitList, match, loading } = props;
  return (
    <div>
      <h2 id="course-unit-heading">
        <Translate contentKey="enrollmentsApp.courseUnit.home.title">Course Units</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="enrollmentsApp.courseUnit.home.createLabel">Create new Course Unit</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {courseUnitList && courseUnitList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="enrollmentsApp.courseUnit.code">Code</Translate>
                </th>
                <th>
                  <Translate contentKey="enrollmentsApp.courseUnit.ects">Ects</Translate>
                </th>
                <th>
                  <Translate contentKey="enrollmentsApp.courseUnit.isGroupOfCourses">Is Group Of Courses</Translate>
                </th>
                <th>
                  <Translate contentKey="enrollmentsApp.courseUnit.isStream">Is Stream</Translate>
                </th>
                <th>
                  <Translate contentKey="enrollmentsApp.courseUnit.isSelectable">Is Selectable</Translate>
                </th>
                <th>
                  <Translate contentKey="enrollmentsApp.courseUnit.selectableModule">Selectable Module</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {courseUnitList.map((courseUnit, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${courseUnit.id}`} color="link" size="sm">
                      {courseUnit.id}
                    </Button>
                  </td>
                  <td>{courseUnit.code}</td>
                  <td>{courseUnit.ects}</td>
                  <td>{courseUnit.isGroupOfCourses ? 'true' : 'false'}</td>
                  <td>{courseUnit.isStream ? 'true' : 'false'}</td>
                  <td>{courseUnit.isSelectable ? 'true' : 'false'}</td>
                  <td>
                    {courseUnit.selectableModuleId ? (
                      <Link to={`selectable-module/${courseUnit.selectableModuleId}`}>{courseUnit.selectableModuleId}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${courseUnit.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${courseUnit.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${courseUnit.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="enrollmentsApp.courseUnit.home.notFound">No Course Units found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ courseUnit }: IRootState) => ({
  courseUnitList: courseUnit.entities,
  loading: courseUnit.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CourseUnit);

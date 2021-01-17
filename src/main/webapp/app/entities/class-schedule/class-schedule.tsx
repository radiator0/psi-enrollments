import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './class-schedule.reducer';
import { IClassSchedule } from 'app/shared/model/class-schedule.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IClassScheduleProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const ClassSchedule = (props: IClassScheduleProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { classScheduleList, match, loading } = props;
  return (
    <div>
      <h2 id="class-schedule-heading">
        <Translate contentKey="enrollmentsApp.classSchedule.home.title">Class Schedules</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="enrollmentsApp.classSchedule.home.createLabel">Create new Class Schedule</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {classScheduleList && classScheduleList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="enrollmentsApp.classSchedule.dayOfWeek">Day Of Week</Translate>
                </th>
                <th>
                  <Translate contentKey="enrollmentsApp.classSchedule.weekType">Week Type</Translate>
                </th>
                <th>
                  <Translate contentKey="enrollmentsApp.classSchedule.semesterPeriod">Semester Period</Translate>
                </th>
                <th>
                  <Translate contentKey="enrollmentsApp.classSchedule.startTime">Start Time</Translate>
                </th>
                <th>
                  <Translate contentKey="enrollmentsApp.classSchedule.endTime">End Time</Translate>
                </th>
                <th>
                  <Translate contentKey="enrollmentsApp.classSchedule.lecturer">Lecturer</Translate>
                </th>
                <th>
                  <Translate contentKey="enrollmentsApp.classSchedule.classGroup">Class Group</Translate>
                </th>
                <th>
                  <Translate contentKey="enrollmentsApp.classSchedule.room">Room</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {classScheduleList.map((classSchedule, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${classSchedule.id}`} color="link" size="sm">
                      {classSchedule.id}
                    </Button>
                  </td>
                  <td>
                    <Translate contentKey={`enrollmentsApp.DayOfWeek.${classSchedule.dayOfWeek}`} />
                  </td>
                  <td>
                    <Translate contentKey={`enrollmentsApp.WeekType.${classSchedule.weekType}`} />
                  </td>
                  <td>
                    <Translate contentKey={`enrollmentsApp.SemesterPeriod.${classSchedule.semesterPeriod}`} />
                  </td>
                  <td>
                    {classSchedule.startTime ? <TextFormat type="date" value={classSchedule.startTime} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {classSchedule.endTime ? <TextFormat type="date" value={classSchedule.endTime} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {classSchedule.lecturerId ? <Link to={`lecturer/${classSchedule.lecturerId}`}>{classSchedule.lecturerId}</Link> : ''}
                  </td>
                  <td>
                    {classSchedule.classGroupId ? (
                      <Link to={`class-group/${classSchedule.classGroupId}`}>{classSchedule.classGroupId}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>{classSchedule.roomId ? <Link to={`room/${classSchedule.roomId}`}>{classSchedule.roomId}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${classSchedule.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${classSchedule.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${classSchedule.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="enrollmentsApp.classSchedule.home.notFound">No Class Schedules found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ classSchedule }: IRootState) => ({
  classScheduleList: classSchedule.entities,
  loading: classSchedule.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ClassSchedule);

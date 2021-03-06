import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ILecturer } from 'app/shared/model/lecturer.model';
import { getEntities as getLecturers } from 'app/entities/lecturer/lecturer.reducer';
import { IClassGroup } from 'app/shared/model/class-group.model';
import { getEntities as getClassGroups } from 'app/entities/class-group/class-group.reducer';
import { IRoom } from 'app/shared/model/room.model';
import { getEntities as getRooms } from 'app/entities/room/room.reducer';
import { getEntity, updateEntity, createEntity, reset } from './class-schedule.reducer';
import { IClassSchedule } from 'app/shared/model/class-schedule.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IClassScheduleUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ClassScheduleUpdate = (props: IClassScheduleUpdateProps) => {
  const [lecturerId, setLecturerId] = useState('0');
  const [classGroupId, setClassGroupId] = useState('0');
  const [roomId, setRoomId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { classScheduleEntity, lecturers, classGroups, rooms, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/class-schedule');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getLecturers();
    props.getClassGroups();
    props.getRooms();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.startTime = convertDateTimeToServer(values.startTime);
    values.endTime = convertDateTimeToServer(values.endTime);

    if (errors.length === 0) {
      const entity = {
        ...classScheduleEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="enrollmentsApp.classSchedule.home.createOrEditLabel">
            <Translate contentKey="enrollmentsApp.classSchedule.home.createOrEditLabel">Create or edit a ClassSchedule</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : classScheduleEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="class-schedule-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="class-schedule-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="dayOfWeekLabel" for="class-schedule-dayOfWeek">
                  <Translate contentKey="enrollmentsApp.classSchedule.dayOfWeek">Day Of Week</Translate>
                </Label>
                <AvInput
                  id="class-schedule-dayOfWeek"
                  type="select"
                  className="form-control"
                  name="dayOfWeek"
                  value={(!isNew && classScheduleEntity.dayOfWeek) || 'Monday'}
                >
                  <option value="Monday">{translate('enrollmentsApp.DayOfWeek.Monday')}</option>
                  <option value="Tuesday">{translate('enrollmentsApp.DayOfWeek.Tuesday')}</option>
                  <option value="Wednesday">{translate('enrollmentsApp.DayOfWeek.Wednesday')}</option>
                  <option value="Thursday">{translate('enrollmentsApp.DayOfWeek.Thursday')}</option>
                  <option value="Friday">{translate('enrollmentsApp.DayOfWeek.Friday')}</option>
                  <option value="Saturday">{translate('enrollmentsApp.DayOfWeek.Saturday')}</option>
                  <option value="Sunday">{translate('enrollmentsApp.DayOfWeek.Sunday')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="weekTypeLabel" for="class-schedule-weekType">
                  <Translate contentKey="enrollmentsApp.classSchedule.weekType">Week Type</Translate>
                </Label>
                <AvInput
                  id="class-schedule-weekType"
                  type="select"
                  className="form-control"
                  name="weekType"
                  value={(!isNew && classScheduleEntity.weekType) || 'All'}
                >
                  <option value="All">{translate('enrollmentsApp.WeekType.All')}</option>
                  <option value="Even">{translate('enrollmentsApp.WeekType.Even')}</option>
                  <option value="Odd">{translate('enrollmentsApp.WeekType.Odd')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="semesterPeriodLabel" for="class-schedule-semesterPeriod">
                  <Translate contentKey="enrollmentsApp.classSchedule.semesterPeriod">Semester Period</Translate>
                </Label>
                <AvInput
                  id="class-schedule-semesterPeriod"
                  type="select"
                  className="form-control"
                  name="semesterPeriod"
                  value={(!isNew && classScheduleEntity.semesterPeriod) || 'Whole'}
                >
                  <option value="Whole">{translate('enrollmentsApp.SemesterPeriod.Whole')}</option>
                  <option value="FirstHalf">{translate('enrollmentsApp.SemesterPeriod.FirstHalf')}</option>
                  <option value="SecondHalf">{translate('enrollmentsApp.SemesterPeriod.SecondHalf')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="startTimeLabel" for="class-schedule-startTime">
                  <Translate contentKey="enrollmentsApp.classSchedule.startTime">Start Time</Translate>
                </Label>
                <AvInput
                  id="class-schedule-startTime"
                  type="datetime-local"
                  className="form-control"
                  name="startTime"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.classScheduleEntity.startTime)}
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="endTimeLabel" for="class-schedule-endTime">
                  <Translate contentKey="enrollmentsApp.classSchedule.endTime">End Time</Translate>
                </Label>
                <AvInput
                  id="class-schedule-endTime"
                  type="datetime-local"
                  className="form-control"
                  name="endTime"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.classScheduleEntity.endTime)}
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label for="class-schedule-lecturer">
                  <Translate contentKey="enrollmentsApp.classSchedule.lecturer">Lecturer</Translate>
                </Label>
                <AvInput id="class-schedule-lecturer" type="select" className="form-control" name="lecturerId" required>
                  {lecturers
                    ? lecturers.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
                <AvFeedback>
                  <Translate contentKey="entity.validation.required">This field is required.</Translate>
                </AvFeedback>
              </AvGroup>
              <AvGroup>
                <Label for="class-schedule-classGroup">
                  <Translate contentKey="enrollmentsApp.classSchedule.classGroup">Class Group</Translate>
                </Label>
                <AvInput id="class-schedule-classGroup" type="select" className="form-control" name="classGroupId" required>
                  {classGroups
                    ? classGroups.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
                <AvFeedback>
                  <Translate contentKey="entity.validation.required">This field is required.</Translate>
                </AvFeedback>
              </AvGroup>
              <AvGroup>
                <Label for="class-schedule-room">
                  <Translate contentKey="enrollmentsApp.classSchedule.room">Room</Translate>
                </Label>
                <AvInput id="class-schedule-room" type="select" className="form-control" name="roomId">
                  <option value="" key="0" />
                  {rooms
                    ? rooms.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/class-schedule" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  lecturers: storeState.lecturer.entities,
  classGroups: storeState.classGroup.entities,
  rooms: storeState.room.entities,
  classScheduleEntity: storeState.classSchedule.entity,
  loading: storeState.classSchedule.loading,
  updating: storeState.classSchedule.updating,
  updateSuccess: storeState.classSchedule.updateSuccess,
});

const mapDispatchToProps = {
  getLecturers,
  getClassGroups,
  getRooms,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ClassScheduleUpdate);

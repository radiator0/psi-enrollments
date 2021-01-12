import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './class-schedule.reducer';
import { IClassSchedule } from 'app/shared/model/class-schedule.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IClassScheduleDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ClassScheduleDetail = (props: IClassScheduleDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { classScheduleEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="enrollmentsApp.classSchedule.detail.title">ClassSchedule</Translate> [<b>{classScheduleEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="dayOfWeek">
              <Translate contentKey="enrollmentsApp.classSchedule.dayOfWeek">Day Of Week</Translate>
            </span>
          </dt>
          <dd>{classScheduleEntity.dayOfWeek}</dd>
          <dt>
            <span id="weekType">
              <Translate contentKey="enrollmentsApp.classSchedule.weekType">Week Type</Translate>
            </span>
          </dt>
          <dd>{classScheduleEntity.weekType}</dd>
          <dt>
            <span id="semesterPeriod">
              <Translate contentKey="enrollmentsApp.classSchedule.semesterPeriod">Semester Period</Translate>
            </span>
          </dt>
          <dd>{classScheduleEntity.semesterPeriod}</dd>
          <dt>
            <span id="startTime">
              <Translate contentKey="enrollmentsApp.classSchedule.startTime">Start Time</Translate>
            </span>
          </dt>
          <dd>
            {classScheduleEntity.startTime ? (
              <TextFormat value={classScheduleEntity.startTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="endTime">
              <Translate contentKey="enrollmentsApp.classSchedule.endTime">End Time</Translate>
            </span>
          </dt>
          <dd>
            {classScheduleEntity.endTime ? <TextFormat value={classScheduleEntity.endTime} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="enrollmentsApp.classSchedule.classGroup">Class Group</Translate>
          </dt>
          <dd>{classScheduleEntity.classGroupId ? classScheduleEntity.classGroupId : ''}</dd>
          <dt>
            <Translate contentKey="enrollmentsApp.classSchedule.room">Room</Translate>
          </dt>
          <dd>{classScheduleEntity.roomId ? classScheduleEntity.roomId : ''}</dd>
        </dl>
        <Button tag={Link} to="/class-schedule" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/class-schedule/${classScheduleEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ classSchedule }: IRootState) => ({
  classScheduleEntity: classSchedule.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ClassScheduleDetail);

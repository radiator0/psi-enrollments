import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './class-unit.reducer';
import { IClassUnit } from 'app/shared/model/class-unit.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IClassUnitDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ClassUnitDetail = (props: IClassUnitDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { classUnitEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="enrollmentsApp.classUnit.detail.title">ClassUnit</Translate> [<b>{classUnitEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="day">
              <Translate contentKey="enrollmentsApp.classUnit.day">Day</Translate>
            </span>
          </dt>
          <dd>{classUnitEntity.day ? <TextFormat value={classUnitEntity.day} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="startTime">
              <Translate contentKey="enrollmentsApp.classUnit.startTime">Start Time</Translate>
            </span>
          </dt>
          <dd>
            {classUnitEntity.startTime ? <TextFormat value={classUnitEntity.startTime} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="endTime">
              <Translate contentKey="enrollmentsApp.classUnit.endTime">End Time</Translate>
            </span>
          </dt>
          <dd>{classUnitEntity.endTime ? <TextFormat value={classUnitEntity.endTime} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="enrollmentsApp.classUnit.classGroup">Class Group</Translate>
          </dt>
          <dd>{classUnitEntity.classGroupId ? classUnitEntity.classGroupId : ''}</dd>
          <dt>
            <Translate contentKey="enrollmentsApp.classUnit.room">Room</Translate>
          </dt>
          <dd>{classUnitEntity.roomId ? classUnitEntity.roomId : ''}</dd>
        </dl>
        <Button tag={Link} to="/class-unit" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/class-unit/${classUnitEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ classUnit }: IRootState) => ({
  classUnitEntity: classUnit.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ClassUnitDetail);

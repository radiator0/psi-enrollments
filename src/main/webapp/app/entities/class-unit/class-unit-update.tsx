import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IClassGroup } from 'app/shared/model/class-group.model';
import { getEntities as getClassGroups } from 'app/entities/class-group/class-group.reducer';
import { IRoom } from 'app/shared/model/room.model';
import { getEntities as getRooms } from 'app/entities/room/room.reducer';
import { getEntity, updateEntity, createEntity, reset } from './class-unit.reducer';
import { IClassUnit } from 'app/shared/model/class-unit.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IClassUnitUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ClassUnitUpdate = (props: IClassUnitUpdateProps) => {
  const [classGroupId, setClassGroupId] = useState('0');
  const [roomId, setRoomId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { classUnitEntity, classGroups, rooms, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/class-unit');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

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
        ...classUnitEntity,
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
          <h2 id="enrollmentsApp.classUnit.home.createOrEditLabel">
            <Translate contentKey="enrollmentsApp.classUnit.home.createOrEditLabel">Create or edit a ClassUnit</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : classUnitEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="class-unit-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="class-unit-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="dayLabel" for="class-unit-day">
                  <Translate contentKey="enrollmentsApp.classUnit.day">Day</Translate>
                </Label>
                <AvField id="class-unit-day" type="date" className="form-control" name="day" />
              </AvGroup>
              <AvGroup>
                <Label id="startTimeLabel" for="class-unit-startTime">
                  <Translate contentKey="enrollmentsApp.classUnit.startTime">Start Time</Translate>
                </Label>
                <AvInput
                  id="class-unit-startTime"
                  type="datetime-local"
                  className="form-control"
                  name="startTime"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.classUnitEntity.startTime)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="endTimeLabel" for="class-unit-endTime">
                  <Translate contentKey="enrollmentsApp.classUnit.endTime">End Time</Translate>
                </Label>
                <AvInput
                  id="class-unit-endTime"
                  type="datetime-local"
                  className="form-control"
                  name="endTime"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.classUnitEntity.endTime)}
                />
              </AvGroup>
              <AvGroup>
                <Label for="class-unit-classGroup">
                  <Translate contentKey="enrollmentsApp.classUnit.classGroup">Class Group</Translate>
                </Label>
                <AvInput id="class-unit-classGroup" type="select" className="form-control" name="classGroupId">
                  <option value="" key="0" />
                  {classGroups
                    ? classGroups.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="class-unit-room">
                  <Translate contentKey="enrollmentsApp.classUnit.room">Room</Translate>
                </Label>
                <AvInput id="class-unit-room" type="select" className="form-control" name="roomId">
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
              <Button tag={Link} id="cancel-save" to="/class-unit" replace color="info">
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
  classGroups: storeState.classGroup.entities,
  rooms: storeState.room.entities,
  classUnitEntity: storeState.classUnit.entity,
  loading: storeState.classUnit.loading,
  updating: storeState.classUnit.updating,
  updateSuccess: storeState.classUnit.updateSuccess,
});

const mapDispatchToProps = {
  getClassGroups,
  getRooms,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ClassUnitUpdate);

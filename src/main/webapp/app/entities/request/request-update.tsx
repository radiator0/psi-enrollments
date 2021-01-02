import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IClassGroup } from 'app/shared/model/class-group.model';
import { getEntities as getClassGroups } from 'app/entities/class-group/class-group.reducer';
import { IStudent } from 'app/shared/model/student.model';
import { getEntities as getStudents } from 'app/entities/student/student.reducer';
import { getEntity, updateEntity, createEntity, reset } from './request.reducer';
import { IRequest } from 'app/shared/model/request.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IRequestUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IRequestUpdateState {
  isNew: boolean;
  classGroupId: string;
  studentId: string;
}

export class RequestUpdate extends React.Component<IRequestUpdateProps, IRequestUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      classGroupId: '0',
      studentId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getClassGroups();
    this.props.getStudents();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { requestEntity } = this.props;
      const entity = {
        ...requestEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/request');
  };

  render() {
    const { requestEntity, classGroups, students, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="enrollmentsApp.request.home.createOrEditLabel">
              <Translate contentKey="enrollmentsApp.request.home.createOrEditLabel">Create or edit a Request</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : requestEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="request-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="request-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="uuidLabel" for="request-uuid">
                    <Translate contentKey="enrollmentsApp.request.uuid">Uuid</Translate>
                  </Label>
                  <AvField
                    id="request-uuid"
                    type="text"
                    name="uuid"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="dateLabel" for="request-date">
                    <Translate contentKey="enrollmentsApp.request.date">Date</Translate>
                  </Label>
                  <AvField
                    id="request-date"
                    type="date"
                    className="form-control"
                    name="date"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="textLabel" for="request-text">
                    <Translate contentKey="enrollmentsApp.request.text">Text</Translate>
                  </Label>
                  <AvField
                    id="request-text"
                    type="text"
                    name="text"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="isExaminedLabel" check>
                    <AvInput id="request-isExamined" type="checkbox" className="form-control" name="isExamined" />
                    <Translate contentKey="enrollmentsApp.request.isExamined">Is Examined</Translate>
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label for="request-classGroup">
                    <Translate contentKey="enrollmentsApp.request.classGroup">Class Group</Translate>
                  </Label>
                  <AvInput id="request-classGroup" type="select" className="form-control" name="classGroupId">
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
                  <Label for="request-student">
                    <Translate contentKey="enrollmentsApp.request.student">Student</Translate>
                  </Label>
                  <AvInput id="request-student" type="select" className="form-control" name="studentId">
                    <option value="" key="0" />
                    {students
                      ? students.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/request" replace color="info">
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
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  classGroups: storeState.classGroup.entities,
  students: storeState.student.entities,
  requestEntity: storeState.request.entity,
  loading: storeState.request.loading,
  updating: storeState.request.updating,
  updateSuccess: storeState.request.updateSuccess
});

const mapDispatchToProps = {
  getClassGroups,
  getStudents,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(RequestUpdate);

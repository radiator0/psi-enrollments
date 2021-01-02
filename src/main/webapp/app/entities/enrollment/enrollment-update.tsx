import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IStudent } from 'app/shared/model/student.model';
import { getEntities as getStudents } from 'app/entities/student/student.reducer';
import { IClassGroup } from 'app/shared/model/class-group.model';
import { getEntities as getClassGroups } from 'app/entities/class-group/class-group.reducer';
import { getEntity, updateEntity, createEntity, reset } from './enrollment.reducer';
import { IEnrollment } from 'app/shared/model/enrollment.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IEnrollmentUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IEnrollmentUpdateState {
  isNew: boolean;
  studentId: string;
  classGroupId: string;
}

export class EnrollmentUpdate extends React.Component<IEnrollmentUpdateProps, IEnrollmentUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      studentId: '0',
      classGroupId: '0',
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

    this.props.getStudents();
    this.props.getClassGroups();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { enrollmentEntity } = this.props;
      const entity = {
        ...enrollmentEntity,
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
    this.props.history.push('/entity/enrollment');
  };

  render() {
    const { enrollmentEntity, students, classGroups, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="enrollmentsApp.enrollment.home.createOrEditLabel">
              <Translate contentKey="enrollmentsApp.enrollment.home.createOrEditLabel">Create or edit a Enrollment</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : enrollmentEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="enrollment-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="enrollment-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="dateLabel" for="enrollment-date">
                    <Translate contentKey="enrollmentsApp.enrollment.date">Date</Translate>
                  </Label>
                  <AvField
                    id="enrollment-date"
                    type="date"
                    className="form-control"
                    name="date"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="isAdministrativeLabel" check>
                    <AvInput id="enrollment-isAdministrative" type="checkbox" className="form-control" name="isAdministrative" />
                    <Translate contentKey="enrollmentsApp.enrollment.isAdministrative">Is Administrative</Translate>
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label for="enrollment-student">
                    <Translate contentKey="enrollmentsApp.enrollment.student">Student</Translate>
                  </Label>
                  <AvInput id="enrollment-student" type="select" className="form-control" name="studentId">
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
                <AvGroup>
                  <Label for="enrollment-classGroup">
                    <Translate contentKey="enrollmentsApp.enrollment.classGroup">Class Group</Translate>
                  </Label>
                  <AvInput id="enrollment-classGroup" type="select" className="form-control" name="classGroupId">
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
                <Button tag={Link} id="cancel-save" to="/entity/enrollment" replace color="info">
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
  students: storeState.student.entities,
  classGroups: storeState.classGroup.entities,
  enrollmentEntity: storeState.enrollment.entity,
  loading: storeState.enrollment.loading,
  updating: storeState.enrollment.updating,
  updateSuccess: storeState.enrollment.updateSuccess
});

const mapDispatchToProps = {
  getStudents,
  getClassGroups,
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
)(EnrollmentUpdate);

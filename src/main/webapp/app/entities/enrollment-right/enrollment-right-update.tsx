import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IEnrollmentDate } from 'app/shared/model/enrollment-date.model';
import { getEntities as getEnrollmentDates } from 'app/entities/enrollment-date/enrollment-date.reducer';
import { ISpecialty } from 'app/shared/model/specialty.model';
import { getEntities as getSpecialties } from 'app/entities/specialty/specialty.reducer';
import { IStudent } from 'app/shared/model/student.model';
import { getEntities as getStudents } from 'app/entities/student/student.reducer';
import { getEntity, updateEntity, createEntity, reset } from './enrollment-right.reducer';
import { IEnrollmentRight } from 'app/shared/model/enrollment-right.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IEnrollmentRightUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IEnrollmentRightUpdateState {
  isNew: boolean;
  enrollmentDateId: string;
  specialtyId: string;
  studentId: string;
}

export class EnrollmentRightUpdate extends React.Component<IEnrollmentRightUpdateProps, IEnrollmentRightUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      enrollmentDateId: '0',
      specialtyId: '0',
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

    this.props.getEnrollmentDates();
    this.props.getSpecialties();
    this.props.getStudents();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { enrollmentRightEntity } = this.props;
      const entity = {
        ...enrollmentRightEntity,
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
    this.props.history.push('/entity/enrollment-right');
  };

  render() {
    const { enrollmentRightEntity, enrollmentDates, specialties, students, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="enrollmentsApp.enrollmentRight.home.createOrEditLabel">
              <Translate contentKey="enrollmentsApp.enrollmentRight.home.createOrEditLabel">Create or edit a EnrollmentRight</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : enrollmentRightEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="enrollment-right-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="enrollment-right-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="startDateLabel" for="enrollment-right-startDate">
                    <Translate contentKey="enrollmentsApp.enrollmentRight.startDate">Start Date</Translate>
                  </Label>
                  <AvField
                    id="enrollment-right-startDate"
                    type="date"
                    className="form-control"
                    name="startDate"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label for="enrollment-right-enrollmentDate">
                    <Translate contentKey="enrollmentsApp.enrollmentRight.enrollmentDate">Enrollment Date</Translate>
                  </Label>
                  <AvInput id="enrollment-right-enrollmentDate" type="select" className="form-control" name="enrollmentDateId">
                    <option value="" key="0" />
                    {enrollmentDates
                      ? enrollmentDates.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="enrollment-right-specialty">
                    <Translate contentKey="enrollmentsApp.enrollmentRight.specialty">Specialty</Translate>
                  </Label>
                  <AvInput id="enrollment-right-specialty" type="select" className="form-control" name="specialtyId">
                    <option value="" key="0" />
                    {specialties
                      ? specialties.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="enrollment-right-student">
                    <Translate contentKey="enrollmentsApp.enrollmentRight.student">Student</Translate>
                  </Label>
                  <AvInput id="enrollment-right-student" type="select" className="form-control" name="studentId">
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
                <Button tag={Link} id="cancel-save" to="/entity/enrollment-right" replace color="info">
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
  enrollmentDates: storeState.enrollmentDate.entities,
  specialties: storeState.specialty.entities,
  students: storeState.student.entities,
  enrollmentRightEntity: storeState.enrollmentRight.entity,
  loading: storeState.enrollmentRight.loading,
  updating: storeState.enrollmentRight.updating,
  updateSuccess: storeState.enrollmentRight.updateSuccess
});

const mapDispatchToProps = {
  getEnrollmentDates,
  getSpecialties,
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
)(EnrollmentRightUpdate);

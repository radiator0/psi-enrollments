import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ICourse } from 'app/shared/model/course.model';
import { getEntities as getCourses } from 'app/entities/course/course.reducer';
import { ILecturer } from 'app/shared/model/lecturer.model';
import { getEntities as getLecturers } from 'app/entities/lecturer/lecturer.reducer';
import { getEntity, updateEntity, createEntity, reset } from './class-group.reducer';
import { IClassGroup } from 'app/shared/model/class-group.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IClassGroupUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IClassGroupUpdateState {
  isNew: boolean;
  courseId: string;
  lecturerId: string;
}

export class ClassGroupUpdate extends React.Component<IClassGroupUpdateProps, IClassGroupUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      courseId: '0',
      lecturerId: '0',
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

    this.props.getCourses();
    this.props.getLecturers();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { classGroupEntity } = this.props;
      const entity = {
        ...classGroupEntity,
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
    this.props.history.push('/entity/class-group');
  };

  render() {
    const { classGroupEntity, courses, lecturers, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="enrollmentsApp.classGroup.home.createOrEditLabel">
              <Translate contentKey="enrollmentsApp.classGroup.home.createOrEditLabel">Create or edit a ClassGroup</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : classGroupEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="class-group-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="class-group-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="codeLabel" for="class-group-code">
                    <Translate contentKey="enrollmentsApp.classGroup.code">Code</Translate>
                  </Label>
                  <AvField
                    id="class-group-code"
                    type="text"
                    name="code"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="isEnrollmentAboveLimitAllowedLabel" check>
                    <AvInput
                      id="class-group-isEnrollmentAboveLimitAllowed"
                      type="checkbox"
                      className="form-control"
                      name="isEnrollmentAboveLimitAllowed"
                    />
                    <Translate contentKey="enrollmentsApp.classGroup.isEnrollmentAboveLimitAllowed">
                      Is Enrollment Above Limit Allowed
                    </Translate>
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label id="peopleLimitLabel" for="class-group-peopleLimit">
                    <Translate contentKey="enrollmentsApp.classGroup.peopleLimit">People Limit</Translate>
                  </Label>
                  <AvField
                    id="class-group-peopleLimit"
                    type="string"
                    className="form-control"
                    name="peopleLimit"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') },
                      min: { value: 0, errorMessage: translate('entity.validation.min', { min: 0 }) },
                      number: { value: true, errorMessage: translate('entity.validation.number') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="enrolledCountLabel" for="class-group-enrolledCount">
                    <Translate contentKey="enrollmentsApp.classGroup.enrolledCount">Enrolled Count</Translate>
                  </Label>
                  <AvField
                    id="class-group-enrolledCount"
                    type="string"
                    className="form-control"
                    name="enrolledCount"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') },
                      min: { value: 0, errorMessage: translate('entity.validation.min', { min: 0 }) },
                      number: { value: true, errorMessage: translate('entity.validation.number') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="isFullLabel" check>
                    <AvInput id="class-group-isFull" type="checkbox" className="form-control" name="isFull" />
                    <Translate contentKey="enrollmentsApp.classGroup.isFull">Is Full</Translate>
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label for="class-group-course">
                    <Translate contentKey="enrollmentsApp.classGroup.course">Course</Translate>
                  </Label>
                  <AvInput id="class-group-course" type="select" className="form-control" name="courseId">
                    <option value="" key="0" />
                    {courses
                      ? courses.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="class-group-lecturer">
                    <Translate contentKey="enrollmentsApp.classGroup.lecturer">Lecturer</Translate>
                  </Label>
                  <AvInput id="class-group-lecturer" type="select" className="form-control" name="lecturerId">
                    <option value="" key="0" />
                    {lecturers
                      ? lecturers.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/class-group" replace color="info">
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
  courses: storeState.course.entities,
  lecturers: storeState.lecturer.entities,
  classGroupEntity: storeState.classGroup.entity,
  loading: storeState.classGroup.loading,
  updating: storeState.classGroup.updating,
  updateSuccess: storeState.classGroup.updateSuccess
});

const mapDispatchToProps = {
  getCourses,
  getLecturers,
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
)(ClassGroupUpdate);

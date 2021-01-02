import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IFieldOfStudy } from 'app/shared/model/field-of-study.model';
import { getEntities as getFieldOfStudies } from 'app/entities/field-of-study/field-of-study.reducer';
import { getEntity, updateEntity, createEntity, reset } from './semester.reducer';
import { ISemester } from 'app/shared/model/semester.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ISemesterUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface ISemesterUpdateState {
  isNew: boolean;
  fieldOfStudyId: string;
}

export class SemesterUpdate extends React.Component<ISemesterUpdateProps, ISemesterUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      fieldOfStudyId: '0',
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

    this.props.getFieldOfStudies();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { semesterEntity } = this.props;
      const entity = {
        ...semesterEntity,
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
    this.props.history.push('/entity/semester');
  };

  render() {
    const { semesterEntity, fieldOfStudies, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="enrollmentsApp.semester.home.createOrEditLabel">
              <Translate contentKey="enrollmentsApp.semester.home.createOrEditLabel">Create or edit a Semester</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : semesterEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="semester-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="semester-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="numberLabel" for="semester-number">
                    <Translate contentKey="enrollmentsApp.semester.number">Number</Translate>
                  </Label>
                  <AvField
                    id="semester-number"
                    type="string"
                    className="form-control"
                    name="number"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') },
                      min: { value: 0, errorMessage: translate('entity.validation.min', { min: 0 }) },
                      number: { value: true, errorMessage: translate('entity.validation.number') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="semesterTypeLabel" for="semester-semesterType">
                    <Translate contentKey="enrollmentsApp.semester.semesterType">Semester Type</Translate>
                  </Label>
                  <AvInput
                    id="semester-semesterType"
                    type="select"
                    className="form-control"
                    name="semesterType"
                    value={(!isNew && semesterEntity.semesterType) || 'Winter'}
                  >
                    <option value="Winter">{translate('enrollmentsApp.SemesterType.Winter')}</option>
                    <option value="Summer">{translate('enrollmentsApp.SemesterType.Summer')}</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="startDateLabel" for="semester-startDate">
                    <Translate contentKey="enrollmentsApp.semester.startDate">Start Date</Translate>
                  </Label>
                  <AvField
                    id="semester-startDate"
                    type="date"
                    className="form-control"
                    name="startDate"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label for="semester-fieldOfStudy">
                    <Translate contentKey="enrollmentsApp.semester.fieldOfStudy">Field Of Study</Translate>
                  </Label>
                  <AvInput id="semester-fieldOfStudy" type="select" className="form-control" name="fieldOfStudyId">
                    <option value="" key="0" />
                    {fieldOfStudies
                      ? fieldOfStudies.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/semester" replace color="info">
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
  fieldOfStudies: storeState.fieldOfStudy.entities,
  semesterEntity: storeState.semester.entity,
  loading: storeState.semester.loading,
  updating: storeState.semester.updating,
  updateSuccess: storeState.semester.updateSuccess
});

const mapDispatchToProps = {
  getFieldOfStudies,
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
)(SemesterUpdate);

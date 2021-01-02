import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './study-program.reducer';
import { IStudyProgram } from 'app/shared/model/study-program.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IStudyProgramUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IStudyProgramUpdateState {
  isNew: boolean;
}

export class StudyProgramUpdate extends React.Component<IStudyProgramUpdateProps, IStudyProgramUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
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
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { studyProgramEntity } = this.props;
      const entity = {
        ...studyProgramEntity,
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
    this.props.history.push('/entity/study-program');
  };

  render() {
    const { studyProgramEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="enrollmentsApp.studyProgram.home.createOrEditLabel">
              <Translate contentKey="enrollmentsApp.studyProgram.home.createOrEditLabel">Create or edit a StudyProgram</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : studyProgramEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="study-program-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="study-program-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="startYearLabel" for="study-program-startYear">
                    <Translate contentKey="enrollmentsApp.studyProgram.startYear">Start Year</Translate>
                  </Label>
                  <AvField
                    id="study-program-startYear"
                    type="string"
                    className="form-control"
                    name="startYear"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') },
                      min: { value: 0, errorMessage: translate('entity.validation.min', { min: 0 }) },
                      number: { value: true, errorMessage: translate('entity.validation.number') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="endYearLabel" for="study-program-endYear">
                    <Translate contentKey="enrollmentsApp.studyProgram.endYear">End Year</Translate>
                  </Label>
                  <AvField
                    id="study-program-endYear"
                    type="string"
                    className="form-control"
                    name="endYear"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') },
                      min: { value: 0, errorMessage: translate('entity.validation.min', { min: 0 }) },
                      number: { value: true, errorMessage: translate('entity.validation.number') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="startingSemesterTypeLabel" for="study-program-startingSemesterType">
                    <Translate contentKey="enrollmentsApp.studyProgram.startingSemesterType">Starting Semester Type</Translate>
                  </Label>
                  <AvInput
                    id="study-program-startingSemesterType"
                    type="select"
                    className="form-control"
                    name="startingSemesterType"
                    value={(!isNew && studyProgramEntity.startingSemesterType) || 'Winter'}
                  >
                    <option value="Winter">{translate('enrollmentsApp.SemesterType.Winter')}</option>
                    <option value="Summer">{translate('enrollmentsApp.SemesterType.Summer')}</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="studyTypeLabel" for="study-program-studyType">
                    <Translate contentKey="enrollmentsApp.studyProgram.studyType">Study Type</Translate>
                  </Label>
                  <AvInput
                    id="study-program-studyType"
                    type="select"
                    className="form-control"
                    name="studyType"
                    value={(!isNew && studyProgramEntity.studyType) || 'Engineering'}
                  >
                    <option value="Engineering">{translate('enrollmentsApp.StudyType.Engineering')}</option>
                    <option value="Masters">{translate('enrollmentsApp.StudyType.Masters')}</option>
                    <option value="Bachelor">{translate('enrollmentsApp.StudyType.Bachelor')}</option>
                    <option value="Unified">{translate('enrollmentsApp.StudyType.Unified')}</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="studyFormLabel" for="study-program-studyForm">
                    <Translate contentKey="enrollmentsApp.studyProgram.studyForm">Study Form</Translate>
                  </Label>
                  <AvInput
                    id="study-program-studyForm"
                    type="select"
                    className="form-control"
                    name="studyForm"
                    value={(!isNew && studyProgramEntity.studyForm) || 'Stationary'}
                  >
                    <option value="Stationary">{translate('enrollmentsApp.StudyForm.Stationary')}</option>
                    <option value="NonStationary">{translate('enrollmentsApp.StudyForm.NonStationary')}</option>
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/study-program" replace color="info">
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
  studyProgramEntity: storeState.studyProgram.entity,
  loading: storeState.studyProgram.loading,
  updating: storeState.studyProgram.updating,
  updateSuccess: storeState.studyProgram.updateSuccess
});

const mapDispatchToProps = {
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
)(StudyProgramUpdate);

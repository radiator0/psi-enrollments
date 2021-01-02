import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './student.reducer';
import { IStudent } from 'app/shared/model/student.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IStudentUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IStudentUpdateState {
  isNew: boolean;
}

export class StudentUpdate extends React.Component<IStudentUpdateProps, IStudentUpdateState> {
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
      const { studentEntity } = this.props;
      const entity = {
        ...studentEntity,
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
    this.props.history.push('/entity/student');
  };

  render() {
    const { studentEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="enrollmentsApp.student.home.createOrEditLabel">
              <Translate contentKey="enrollmentsApp.student.home.createOrEditLabel">Create or edit a Student</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : studentEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="student-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="student-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="firstNameLabel" for="student-firstName">
                    <Translate contentKey="enrollmentsApp.student.firstName">First Name</Translate>
                  </Label>
                  <AvField
                    id="student-firstName"
                    type="text"
                    name="firstName"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="secondNameLabel" for="student-secondName">
                    <Translate contentKey="enrollmentsApp.student.secondName">Second Name</Translate>
                  </Label>
                  <AvField id="student-secondName" type="text" name="secondName" />
                </AvGroup>
                <AvGroup>
                  <Label id="lastNameLabel" for="student-lastName">
                    <Translate contentKey="enrollmentsApp.student.lastName">Last Name</Translate>
                  </Label>
                  <AvField
                    id="student-lastName"
                    type="text"
                    name="lastName"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="mailLabel" for="student-mail">
                    <Translate contentKey="enrollmentsApp.student.mail">Mail</Translate>
                  </Label>
                  <AvField
                    id="student-mail"
                    type="text"
                    name="mail"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="titleLabel" for="student-title">
                    <Translate contentKey="enrollmentsApp.student.title">Title</Translate>
                  </Label>
                  <AvField id="student-title" type="text" name="title" />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/student" replace color="info">
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
  studentEntity: storeState.student.entity,
  loading: storeState.student.loading,
  updating: storeState.student.updating,
  updateSuccess: storeState.student.updateSuccess
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
)(StudentUpdate);

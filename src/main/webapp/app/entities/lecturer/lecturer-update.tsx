import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './lecturer.reducer';
import { ILecturer } from 'app/shared/model/lecturer.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ILecturerUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface ILecturerUpdateState {
  isNew: boolean;
}

export class LecturerUpdate extends React.Component<ILecturerUpdateProps, ILecturerUpdateState> {
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
      const { lecturerEntity } = this.props;
      const entity = {
        ...lecturerEntity,
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
    this.props.history.push('/entity/lecturer');
  };

  render() {
    const { lecturerEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="enrollmentsApp.lecturer.home.createOrEditLabel">
              <Translate contentKey="enrollmentsApp.lecturer.home.createOrEditLabel">Create or edit a Lecturer</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : lecturerEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="lecturer-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="lecturer-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="firstNameLabel" for="lecturer-firstName">
                    <Translate contentKey="enrollmentsApp.lecturer.firstName">First Name</Translate>
                  </Label>
                  <AvField
                    id="lecturer-firstName"
                    type="text"
                    name="firstName"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="secondNameLabel" for="lecturer-secondName">
                    <Translate contentKey="enrollmentsApp.lecturer.secondName">Second Name</Translate>
                  </Label>
                  <AvField id="lecturer-secondName" type="text" name="secondName" />
                </AvGroup>
                <AvGroup>
                  <Label id="lastNameLabel" for="lecturer-lastName">
                    <Translate contentKey="enrollmentsApp.lecturer.lastName">Last Name</Translate>
                  </Label>
                  <AvField
                    id="lecturer-lastName"
                    type="text"
                    name="lastName"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="mailLabel" for="lecturer-mail">
                    <Translate contentKey="enrollmentsApp.lecturer.mail">Mail</Translate>
                  </Label>
                  <AvField
                    id="lecturer-mail"
                    type="text"
                    name="mail"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="titleLabel" for="lecturer-title">
                    <Translate contentKey="enrollmentsApp.lecturer.title">Title</Translate>
                  </Label>
                  <AvField id="lecturer-title" type="text" name="title" />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/lecturer" replace color="info">
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
  lecturerEntity: storeState.lecturer.entity,
  loading: storeState.lecturer.loading,
  updating: storeState.lecturer.updating,
  updateSuccess: storeState.lecturer.updateSuccess
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
)(LecturerUpdate);

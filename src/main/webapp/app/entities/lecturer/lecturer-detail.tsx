import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './lecturer.reducer';
import { ILecturer } from 'app/shared/model/lecturer.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ILecturerDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class LecturerDetail extends React.Component<ILecturerDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { lecturerEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="enrollmentsApp.lecturer.detail.title">Lecturer</Translate> [<b>{lecturerEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="firstName">
                <Translate contentKey="enrollmentsApp.lecturer.firstName">First Name</Translate>
              </span>
            </dt>
            <dd>{lecturerEntity.firstName}</dd>
            <dt>
              <span id="secondName">
                <Translate contentKey="enrollmentsApp.lecturer.secondName">Second Name</Translate>
              </span>
            </dt>
            <dd>{lecturerEntity.secondName}</dd>
            <dt>
              <span id="lastName">
                <Translate contentKey="enrollmentsApp.lecturer.lastName">Last Name</Translate>
              </span>
            </dt>
            <dd>{lecturerEntity.lastName}</dd>
            <dt>
              <span id="mail">
                <Translate contentKey="enrollmentsApp.lecturer.mail">Mail</Translate>
              </span>
            </dt>
            <dd>{lecturerEntity.mail}</dd>
            <dt>
              <span id="title">
                <Translate contentKey="enrollmentsApp.lecturer.title">Title</Translate>
              </span>
            </dt>
            <dd>{lecturerEntity.title}</dd>
          </dl>
          <Button tag={Link} to="/entity/lecturer" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/lecturer/${lecturerEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ lecturer }: IRootState) => ({
  lecturerEntity: lecturer.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(LecturerDetail);

import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './student.reducer';
import { IStudent } from 'app/shared/model/student.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IStudentDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class StudentDetail extends React.Component<IStudentDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { studentEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="enrollmentsApp.student.detail.title">Student</Translate> [<b>{studentEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="firstName">
                <Translate contentKey="enrollmentsApp.student.firstName">First Name</Translate>
              </span>
            </dt>
            <dd>{studentEntity.firstName}</dd>
            <dt>
              <span id="secondName">
                <Translate contentKey="enrollmentsApp.student.secondName">Second Name</Translate>
              </span>
            </dt>
            <dd>{studentEntity.secondName}</dd>
            <dt>
              <span id="lastName">
                <Translate contentKey="enrollmentsApp.student.lastName">Last Name</Translate>
              </span>
            </dt>
            <dd>{studentEntity.lastName}</dd>
            <dt>
              <span id="mail">
                <Translate contentKey="enrollmentsApp.student.mail">Mail</Translate>
              </span>
            </dt>
            <dd>{studentEntity.mail}</dd>
            <dt>
              <span id="title">
                <Translate contentKey="enrollmentsApp.student.title">Title</Translate>
              </span>
            </dt>
            <dd>{studentEntity.title}</dd>
          </dl>
          <Button tag={Link} to="/entity/student" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/student/${studentEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ student }: IRootState) => ({
  studentEntity: student.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(StudentDetail);

import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './request.reducer';
import { IRequest } from 'app/shared/model/request.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IRequestDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const RequestDetail = (props: IRequestDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { requestEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="enrollmentsApp.request.detail.title">Request</Translate> [<b>{requestEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="uuid">
              <Translate contentKey="enrollmentsApp.request.uuid">Uuid</Translate>
            </span>
          </dt>
          <dd>{requestEntity.uuid}</dd>
          <dt>
            <span id="date">
              <Translate contentKey="enrollmentsApp.request.date">Date</Translate>
            </span>
          </dt>
          <dd>{requestEntity.date ? <TextFormat value={requestEntity.date} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="text">
              <Translate contentKey="enrollmentsApp.request.text">Text</Translate>
            </span>
          </dt>
          <dd>{requestEntity.text}</dd>
          <dt>
            <span id="isExamined">
              <Translate contentKey="enrollmentsApp.request.isExamined">Is Examined</Translate>
            </span>
          </dt>
          <dd>{requestEntity.isExamined ? 'true' : 'false'}</dd>
          <dt>
            <Translate contentKey="enrollmentsApp.request.classGroup">Class Group</Translate>
          </dt>
          <dd>{requestEntity.classGroupId ? requestEntity.classGroupId : ''}</dd>
          <dt>
            <Translate contentKey="enrollmentsApp.request.student">Student</Translate>
          </dt>
          <dd>{requestEntity.studentId ? requestEntity.studentId : ''}</dd>
        </dl>
        <Button tag={Link} to="/request" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/request/${requestEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ request }: IRootState) => ({
  requestEntity: request.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(RequestDetail);

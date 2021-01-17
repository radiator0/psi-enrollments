import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './enrollment-date.reducer';
import { IEnrollmentDate } from 'app/shared/model/enrollment-date.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEnrollmentDateDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EnrollmentDateDetail = (props: IEnrollmentDateDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { enrollmentDateEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="enrollmentsApp.enrollmentDate.detail.title">EnrollmentDate</Translate> [<b>{enrollmentDateEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="name">
              <Translate contentKey="enrollmentsApp.enrollmentDate.name">Name</Translate>
            </span>
          </dt>
          <dd>{enrollmentDateEntity.name}</dd>
          <dt>
            <span id="isPreEnrollment">
              <Translate contentKey="enrollmentsApp.enrollmentDate.isPreEnrollment">Is Pre Enrollment</Translate>
            </span>
          </dt>
          <dd>{enrollmentDateEntity.isPreEnrollment ? 'true' : 'false'}</dd>
          <dt>
            <span id="startDate">
              <Translate contentKey="enrollmentsApp.enrollmentDate.startDate">Start Date</Translate>
            </span>
          </dt>
          <dd>
            {enrollmentDateEntity.startDate ? (
              <TextFormat value={enrollmentDateEntity.startDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="endDate">
              <Translate contentKey="enrollmentsApp.enrollmentDate.endDate">End Date</Translate>
            </span>
          </dt>
          <dd>
            {enrollmentDateEntity.endDate ? (
              <TextFormat value={enrollmentDateEntity.endDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="enrollmentsApp.enrollmentDate.semester">Semester</Translate>
          </dt>
          <dd>{enrollmentDateEntity.semesterId ? enrollmentDateEntity.semesterId : ''}</dd>
        </dl>
        <Button tag={Link} to="/enrollment-date" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/enrollment-date/${enrollmentDateEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ enrollmentDate }: IRootState) => ({
  enrollmentDateEntity: enrollmentDate.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EnrollmentDateDetail);

import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './enrollment.reducer';
import { IEnrollment } from 'app/shared/model/enrollment.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEnrollmentDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EnrollmentDetail = (props: IEnrollmentDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { enrollmentEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="enrollmentsApp.enrollment.detail.title">Enrollment</Translate> [<b>{enrollmentEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="date">
              <Translate contentKey="enrollmentsApp.enrollment.date">Date</Translate>
            </span>
          </dt>
          <dd>{enrollmentEntity.date ? <TextFormat value={enrollmentEntity.date} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="isAdministrative">
              <Translate contentKey="enrollmentsApp.enrollment.isAdministrative">Is Administrative</Translate>
            </span>
          </dt>
          <dd>{enrollmentEntity.isAdministrative ? 'true' : 'false'}</dd>
          <dt>
            <Translate contentKey="enrollmentsApp.enrollment.student">Student</Translate>
          </dt>
          <dd>{enrollmentEntity.studentId ? enrollmentEntity.studentId : ''}</dd>
          <dt>
            <Translate contentKey="enrollmentsApp.enrollment.classGroup">Class Group</Translate>
          </dt>
          <dd>{enrollmentEntity.classGroupId ? enrollmentEntity.classGroupId : ''}</dd>
        </dl>
        <Button tag={Link} to="/enrollment" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/enrollment/${enrollmentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ enrollment }: IRootState) => ({
  enrollmentEntity: enrollment.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EnrollmentDetail);

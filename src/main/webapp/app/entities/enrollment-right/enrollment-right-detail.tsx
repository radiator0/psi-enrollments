import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './enrollment-right.reducer';
import { IEnrollmentRight } from 'app/shared/model/enrollment-right.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEnrollmentRightDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EnrollmentRightDetail = (props: IEnrollmentRightDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { enrollmentRightEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="enrollmentsApp.enrollmentRight.detail.title">EnrollmentRight</Translate> [<b>{enrollmentRightEntity.id}</b>
          ]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="startDate">
              <Translate contentKey="enrollmentsApp.enrollmentRight.startDate">Start Date</Translate>
            </span>
          </dt>
          <dd>
            {enrollmentRightEntity.startDate ? (
              <TextFormat value={enrollmentRightEntity.startDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="enrollmentsApp.enrollmentRight.enrollmentDate">Enrollment Date</Translate>
          </dt>
          <dd>{enrollmentRightEntity.enrollmentDateId ? enrollmentRightEntity.enrollmentDateId : ''}</dd>
          <dt>
            <Translate contentKey="enrollmentsApp.enrollmentRight.specialty">Specialty</Translate>
          </dt>
          <dd>{enrollmentRightEntity.specialtyId ? enrollmentRightEntity.specialtyId : ''}</dd>
          <dt>
            <Translate contentKey="enrollmentsApp.enrollmentRight.student">Student</Translate>
          </dt>
          <dd>{enrollmentRightEntity.studentId ? enrollmentRightEntity.studentId : ''}</dd>
        </dl>
        <Button tag={Link} to="/enrollment-right" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/enrollment-right/${enrollmentRightEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ enrollmentRight }: IRootState) => ({
  enrollmentRightEntity: enrollmentRight.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EnrollmentRightDetail);

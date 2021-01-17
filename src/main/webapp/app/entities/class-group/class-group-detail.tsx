import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './class-group.reducer';
import { IClassGroup } from 'app/shared/model/class-group.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IClassGroupDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ClassGroupDetail = (props: IClassGroupDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { classGroupEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="enrollmentsApp.classGroup.detail.title">ClassGroup</Translate> [<b>{classGroupEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="code">
              <Translate contentKey="enrollmentsApp.classGroup.code">Code</Translate>
            </span>
          </dt>
          <dd>{classGroupEntity.code}</dd>
          <dt>
            <span id="isEnrollmentAboveLimitAllowed">
              <Translate contentKey="enrollmentsApp.classGroup.isEnrollmentAboveLimitAllowed">Is Enrollment Above Limit Allowed</Translate>
            </span>
          </dt>
          <dd>{classGroupEntity.isEnrollmentAboveLimitAllowed ? 'true' : 'false'}</dd>
          <dt>
            <span id="peopleLimit">
              <Translate contentKey="enrollmentsApp.classGroup.peopleLimit">People Limit</Translate>
            </span>
          </dt>
          <dd>{classGroupEntity.peopleLimit}</dd>
          <dt>
            <span id="enrolledCount">
              <Translate contentKey="enrollmentsApp.classGroup.enrolledCount">Enrolled Count</Translate>
            </span>
          </dt>
          <dd>{classGroupEntity.enrolledCount}</dd>
          <dt>
            <span id="isFull">
              <Translate contentKey="enrollmentsApp.classGroup.isFull">Is Full</Translate>
            </span>
          </dt>
          <dd>{classGroupEntity.isFull ? 'true' : 'false'}</dd>
          <dt>
            <Translate contentKey="enrollmentsApp.classGroup.course">Course</Translate>
          </dt>
          <dd>{classGroupEntity.courseId ? classGroupEntity.courseId : ''}</dd>
          <dt>
            <Translate contentKey="enrollmentsApp.classGroup.mainLecturer">Main Lecturer</Translate>
          </dt>
          <dd>{classGroupEntity.mainLecturerId ? classGroupEntity.mainLecturerId : ''}</dd>
        </dl>
        <Button tag={Link} to="/class-group" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/class-group/${classGroupEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ classGroup }: IRootState) => ({
  classGroupEntity: classGroup.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ClassGroupDetail);

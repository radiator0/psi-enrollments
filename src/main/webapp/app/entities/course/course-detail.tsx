import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './course.reducer';
import { ICourse } from 'app/shared/model/course.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICourseDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class CourseDetail extends React.Component<ICourseDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { courseEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="enrollmentsApp.course.detail.title">Course</Translate> [<b>{courseEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">
                <Translate contentKey="enrollmentsApp.course.name">Name</Translate>
              </span>
            </dt>
            <dd>{courseEntity.name}</dd>
            <dt>
              <span id="shortName">
                <Translate contentKey="enrollmentsApp.course.shortName">Short Name</Translate>
              </span>
            </dt>
            <dd>{courseEntity.shortName}</dd>
            <dt>
              <span id="code">
                <Translate contentKey="enrollmentsApp.course.code">Code</Translate>
              </span>
            </dt>
            <dd>{courseEntity.code}</dd>
            <dt>
              <span id="ects">
                <Translate contentKey="enrollmentsApp.course.ects">Ects</Translate>
              </span>
            </dt>
            <dd>{courseEntity.ects}</dd>
            <dt>
              <span id="form">
                <Translate contentKey="enrollmentsApp.course.form">Form</Translate>
              </span>
            </dt>
            <dd>{courseEntity.form}</dd>
            <dt>
              <Translate contentKey="enrollmentsApp.course.specialty">Specialty</Translate>
            </dt>
            <dd>
              {courseEntity.specialties
                ? courseEntity.specialties.map((val, i) => (
                    <span key={val.id}>
                      <a>{val.id}</a>
                      {i === courseEntity.specialties.length - 1 ? '' : ', '}
                    </span>
                  ))
                : null}
            </dd>
            <dt>
              <Translate contentKey="enrollmentsApp.course.courseUnit">Course Unit</Translate>
            </dt>
            <dd>{courseEntity.courseUnitId ? courseEntity.courseUnitId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/course" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/course/${courseEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ course }: IRootState) => ({
  courseEntity: course.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(CourseDetail);

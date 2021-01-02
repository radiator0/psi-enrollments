import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './study-program.reducer';
import { IStudyProgram } from 'app/shared/model/study-program.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IStudyProgramDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class StudyProgramDetail extends React.Component<IStudyProgramDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { studyProgramEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="enrollmentsApp.studyProgram.detail.title">StudyProgram</Translate> [<b>{studyProgramEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="startYear">
                <Translate contentKey="enrollmentsApp.studyProgram.startYear">Start Year</Translate>
              </span>
            </dt>
            <dd>{studyProgramEntity.startYear}</dd>
            <dt>
              <span id="endYear">
                <Translate contentKey="enrollmentsApp.studyProgram.endYear">End Year</Translate>
              </span>
            </dt>
            <dd>{studyProgramEntity.endYear}</dd>
            <dt>
              <span id="startingSemesterType">
                <Translate contentKey="enrollmentsApp.studyProgram.startingSemesterType">Starting Semester Type</Translate>
              </span>
            </dt>
            <dd>{studyProgramEntity.startingSemesterType}</dd>
            <dt>
              <span id="studyType">
                <Translate contentKey="enrollmentsApp.studyProgram.studyType">Study Type</Translate>
              </span>
            </dt>
            <dd>{studyProgramEntity.studyType}</dd>
            <dt>
              <span id="studyForm">
                <Translate contentKey="enrollmentsApp.studyProgram.studyForm">Study Form</Translate>
              </span>
            </dt>
            <dd>{studyProgramEntity.studyForm}</dd>
          </dl>
          <Button tag={Link} to="/entity/study-program" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/study-program/${studyProgramEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ studyProgram }: IRootState) => ({
  studyProgramEntity: studyProgram.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(StudyProgramDetail);

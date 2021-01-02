import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './field-of-study.reducer';
import { IFieldOfStudy } from 'app/shared/model/field-of-study.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IFieldOfStudyDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class FieldOfStudyDetail extends React.Component<IFieldOfStudyDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { fieldOfStudyEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="enrollmentsApp.fieldOfStudy.detail.title">FieldOfStudy</Translate> [<b>{fieldOfStudyEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">
                <Translate contentKey="enrollmentsApp.fieldOfStudy.name">Name</Translate>
              </span>
            </dt>
            <dd>{fieldOfStudyEntity.name}</dd>
            <dt>
              <span id="uniqueName">
                <Translate contentKey="enrollmentsApp.fieldOfStudy.uniqueName">Unique Name</Translate>
              </span>
            </dt>
            <dd>{fieldOfStudyEntity.uniqueName}</dd>
            <dt>
              <Translate contentKey="enrollmentsApp.fieldOfStudy.studyProgram">Study Program</Translate>
            </dt>
            <dd>{fieldOfStudyEntity.studyProgramId ? fieldOfStudyEntity.studyProgramId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/field-of-study" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/field-of-study/${fieldOfStudyEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ fieldOfStudy }: IRootState) => ({
  fieldOfStudyEntity: fieldOfStudy.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(FieldOfStudyDetail);

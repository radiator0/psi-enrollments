import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './field-of-study.reducer';
import { IFieldOfStudy } from 'app/shared/model/field-of-study.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IFieldOfStudyProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class FieldOfStudy extends React.Component<IFieldOfStudyProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { fieldOfStudyList, match } = this.props;
    return (
      <div>
        <h2 id="field-of-study-heading">
          <Translate contentKey="enrollmentsApp.fieldOfStudy.home.title">Field Of Studies</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="enrollmentsApp.fieldOfStudy.home.createLabel">Create a new Field Of Study</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          {fieldOfStudyList && fieldOfStudyList.length > 0 ? (
            <Table responsive aria-describedby="field-of-study-heading">
              <thead>
                <tr>
                  <th>
                    <Translate contentKey="global.field.id">ID</Translate>
                  </th>
                  <th>
                    <Translate contentKey="enrollmentsApp.fieldOfStudy.name">Name</Translate>
                  </th>
                  <th>
                    <Translate contentKey="enrollmentsApp.fieldOfStudy.uniqueName">Unique Name</Translate>
                  </th>
                  <th>
                    <Translate contentKey="enrollmentsApp.fieldOfStudy.studyProgram">Study Program</Translate>
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {fieldOfStudyList.map((fieldOfStudy, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${fieldOfStudy.id}`} color="link" size="sm">
                        {fieldOfStudy.id}
                      </Button>
                    </td>
                    <td>{fieldOfStudy.name}</td>
                    <td>{fieldOfStudy.uniqueName}</td>
                    <td>
                      {fieldOfStudy.studyProgramId ? (
                        <Link to={`study-program/${fieldOfStudy.studyProgramId}`}>{fieldOfStudy.studyProgramId}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${fieldOfStudy.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${fieldOfStudy.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${fieldOfStudy.id}/delete`} color="danger" size="sm">
                          <FontAwesomeIcon icon="trash" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.delete">Delete</Translate>
                          </span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            <div className="alert alert-warning">
              <Translate contentKey="enrollmentsApp.fieldOfStudy.home.notFound">No Field Of Studies found</Translate>
            </div>
          )}
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ fieldOfStudy }: IRootState) => ({
  fieldOfStudyList: fieldOfStudy.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(FieldOfStudy);

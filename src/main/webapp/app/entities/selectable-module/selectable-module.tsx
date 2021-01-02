import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './selectable-module.reducer';
import { ISelectableModule } from 'app/shared/model/selectable-module.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ISelectableModuleProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class SelectableModule extends React.Component<ISelectableModuleProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { selectableModuleList, match } = this.props;
    return (
      <div>
        <h2 id="selectable-module-heading">
          <Translate contentKey="enrollmentsApp.selectableModule.home.title">Selectable Modules</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="enrollmentsApp.selectableModule.home.createLabel">Create a new Selectable Module</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          {selectableModuleList && selectableModuleList.length > 0 ? (
            <Table responsive aria-describedby="selectable-module-heading">
              <thead>
                <tr>
                  <th>
                    <Translate contentKey="global.field.id">ID</Translate>
                  </th>
                  <th>
                    <Translate contentKey="enrollmentsApp.selectableModule.name">Name</Translate>
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {selectableModuleList.map((selectableModule, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${selectableModule.id}`} color="link" size="sm">
                        {selectableModule.id}
                      </Button>
                    </td>
                    <td>{selectableModule.name}</td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${selectableModule.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${selectableModule.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${selectableModule.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="enrollmentsApp.selectableModule.home.notFound">No Selectable Modules found</Translate>
            </div>
          )}
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ selectableModule }: IRootState) => ({
  selectableModuleList: selectableModule.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(SelectableModule);

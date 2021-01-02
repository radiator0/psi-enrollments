import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, getSortState, IPaginationBaseState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './class-group.reducer';
import { IClassGroup } from 'app/shared/model/class-group.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface IClassGroupProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export type IClassGroupState = IPaginationBaseState;

export class ClassGroup extends React.Component<IClassGroupProps, IClassGroupState> {
  state: IClassGroupState = {
    ...getSortState(this.props.location, ITEMS_PER_PAGE)
  };

  componentDidMount() {
    this.getEntities();
  }

  sort = prop => () => {
    this.setState(
      {
        order: this.state.order === 'asc' ? 'desc' : 'asc',
        sort: prop
      },
      () => this.sortEntities()
    );
  };

  sortEntities() {
    this.getEntities();
    this.props.history.push(`${this.props.location.pathname}?page=${this.state.activePage}&sort=${this.state.sort},${this.state.order}`);
  }

  handlePagination = activePage => this.setState({ activePage }, () => this.sortEntities());

  getEntities = () => {
    const { activePage, itemsPerPage, sort, order } = this.state;
    this.props.getEntities(activePage - 1, itemsPerPage, `${sort},${order}`);
  };

  render() {
    const { classGroupList, match, totalItems } = this.props;
    return (
      <div>
        <h2 id="class-group-heading">
          <Translate contentKey="enrollmentsApp.classGroup.home.title">Class Groups</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="enrollmentsApp.classGroup.home.createLabel">Create a new Class Group</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          {classGroupList && classGroupList.length > 0 ? (
            <Table responsive aria-describedby="class-group-heading">
              <thead>
                <tr>
                  <th className="hand" onClick={this.sort('id')}>
                    <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('code')}>
                    <Translate contentKey="enrollmentsApp.classGroup.code">Code</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('isEnrollmentAboveLimitAllowed')}>
                    <Translate contentKey="enrollmentsApp.classGroup.isEnrollmentAboveLimitAllowed">
                      Is Enrollment Above Limit Allowed
                    </Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('peopleLimit')}>
                    <Translate contentKey="enrollmentsApp.classGroup.peopleLimit">People Limit</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('enrolledCount')}>
                    <Translate contentKey="enrollmentsApp.classGroup.enrolledCount">Enrolled Count</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('isFull')}>
                    <Translate contentKey="enrollmentsApp.classGroup.isFull">Is Full</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="enrollmentsApp.classGroup.course">Course</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="enrollmentsApp.classGroup.lecturer">Lecturer</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {classGroupList.map((classGroup, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${classGroup.id}`} color="link" size="sm">
                        {classGroup.id}
                      </Button>
                    </td>
                    <td>{classGroup.code}</td>
                    <td>{classGroup.isEnrollmentAboveLimitAllowed ? 'true' : 'false'}</td>
                    <td>{classGroup.peopleLimit}</td>
                    <td>{classGroup.enrolledCount}</td>
                    <td>{classGroup.isFull ? 'true' : 'false'}</td>
                    <td>{classGroup.courseId ? <Link to={`course/${classGroup.courseId}`}>{classGroup.courseId}</Link> : ''}</td>
                    <td>{classGroup.lecturerId ? <Link to={`lecturer/${classGroup.lecturerId}`}>{classGroup.lecturerId}</Link> : ''}</td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${classGroup.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${classGroup.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${classGroup.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="enrollmentsApp.classGroup.home.notFound">No Class Groups found</Translate>
            </div>
          )}
        </div>
        <div className={classGroupList && classGroupList.length > 0 ? '' : 'd-none'}>
          <Row className="justify-content-center">
            <JhiItemCount page={this.state.activePage} total={totalItems} itemsPerPage={this.state.itemsPerPage} i18nEnabled />
          </Row>
          <Row className="justify-content-center">
            <JhiPagination
              activePage={this.state.activePage}
              onSelect={this.handlePagination}
              maxButtons={5}
              itemsPerPage={this.state.itemsPerPage}
              totalItems={this.props.totalItems}
            />
          </Row>
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ classGroup }: IRootState) => ({
  classGroupList: classGroup.entities,
  totalItems: classGroup.totalItems
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ClassGroup);

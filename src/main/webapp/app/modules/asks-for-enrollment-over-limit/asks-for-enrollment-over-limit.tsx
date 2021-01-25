import React, { Component } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import Table from '@material-ui/core/Table';
import { Translate, TextFormat, IPaginationBaseState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities, deleteEntity } from 'app/shared/reducers/request.reducer';
import { ButtonGroup, Container, TableBody, TableCell, TableHead, TableRow } from '@material-ui/core';
import { APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { Button } from '@material-ui/core';

export interface IAsksForEnrollmentOverLimitProps extends StateProps, DispatchProps { }

class AsksForEnrollmentOverLimit extends Component<IAsksForEnrollmentOverLimitProps> {

  paginationState: IPaginationBaseState;

  constructor(props) {
    super(props);
    props.getEntities();
  }

  render() {
    const { requestList, loading } = this.props;
    return (
      <React.Fragment>
        {requestList && requestList.length > 0 ? (
          <Table aria-label="simple table">
            <TableHead>
              <TableRow>
                <TableCell>
                  <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </TableCell>
                <TableCell>
                  <Translate contentKey="enrollmentsApp.request.date">Date</Translate> <FontAwesomeIcon icon="sort" />
                </TableCell>
                <TableCell>
                  <Translate contentKey="enrollmentsApp.request.text">Text</Translate> <FontAwesomeIcon icon="sort" />
                </TableCell>
                <TableCell>
                  <Translate contentKey="enrollmentsApp.request.isExamined">Is Examined</Translate> <FontAwesomeIcon icon="sort" />
                </TableCell>
                <TableCell>
                  <Translate contentKey="enrollmentsApp.request.classGroup">Class Group</Translate> <FontAwesomeIcon icon="sort" />
                </TableCell>
                <TableCell>
                  <Translate contentKey="enrollmentsApp.request.student">Student</Translate> <FontAwesomeIcon icon="sort" />
                </TableCell>
                <TableCell />
              </TableRow>
            </TableHead>
            <TableBody>
              {requestList.map((request, i) => (
                <TableRow key={`entity-${i}`}>
                  <TableCell>{request.id}</TableCell>
                  <TableCell>{request.date ? <TextFormat type="date" value={request.date} format={APP_LOCAL_DATE_FORMAT} /> : null}</TableCell>
                  <TableCell>{request.text}</TableCell>
                  <TableCell>{request.isExamined ? 'true' : 'false'}</TableCell>
                  <TableCell>{request.classGroupId ? <Link to={`class-group/${request.classGroupId}`}>{request.classGroupId}</Link> : ''}</TableCell>
                  <TableCell>{request.studentId ? <Link to={`student/${request.studentId}`}>{request.studentId}</Link> : ''}</TableCell>
                  <TableCell className="text-right">
                    <ButtonGroup variant="contained" color="primary" aria-label="contained primary button group">
                      <Button>
                        <Translate contentKey="entity.action.view">View</Translate>
                      </Button>
                      <Button
                        onClick={() => this.props.deleteEntity(request.id)}
                        startIcon={<FontAwesomeIcon icon="trash" />}
                        color="secondary">
                        <Translate contentKey="entity.action.delete">Delete</Translate>
                      </Button>
                    </ButtonGroup>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        ) : (
            !loading && (
              <Container className="alert alert-warning">
                <Translate contentKey="enrollmentsApp.request.home.notFound">No Requests found</Translate>
              </Container>
            )
          )}
      </React.Fragment>
    );
  }
}

const mapStateToProps = ({ request }: IRootState) => ({
  requestList: request.entities,
  loading: request.loading,
  totalItems: request.totalItems,
});

const mapDispatchToProps = {
  getEntities,
  deleteEntity,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(AsksForEnrollmentOverLimit);

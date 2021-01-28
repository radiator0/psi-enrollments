import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IRequest, defaultValue } from 'app/shared/model/request.model';

export const ACTION_TYPES = {
  FETCH_REQUEST_LIST: 'request/FETCH_REQUEST_LIST',
  FETCH_REQUEST: 'request/FETCH_REQUEST',
  CREATE_REQUEST: 'request/CREATE_REQUEST',
  UPDATE_REQUEST: 'request/UPDATE_REQUEST',
  DELETE_REQUEST: 'request/DELETE_REQUEST',
  ACCEPT_REQUEST: 'request/ACCEPT_REQUEST',
  DECLINE_REQUEST: 'request/DECLINE_REQUEST',
  NOT_EXAMINED_COUNT: 'request/NOT_EXAMINED_COUNT',
  RESET: 'request/RESET',
  LOGOUT: 'authentication/LOGOUT',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IRequest>,
  entity: defaultValue,
  notExaminedCount: 0,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type RequestState = Readonly<typeof initialState>;

// Reducer

export default (state: RequestState = initialState, action): RequestState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_REQUEST_LIST):
    case REQUEST(ACTION_TYPES.FETCH_REQUEST):
    case REQUEST(ACTION_TYPES.NOT_EXAMINED_COUNT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_REQUEST):
    case REQUEST(ACTION_TYPES.UPDATE_REQUEST):
    case REQUEST(ACTION_TYPES.DELETE_REQUEST):
    case REQUEST(ACTION_TYPES.ACCEPT_REQUEST):
    case REQUEST(ACTION_TYPES.DECLINE_REQUEST):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_REQUEST_LIST):
    case FAILURE(ACTION_TYPES.FETCH_REQUEST):
    case FAILURE(ACTION_TYPES.CREATE_REQUEST):
    case FAILURE(ACTION_TYPES.UPDATE_REQUEST):
    case FAILURE(ACTION_TYPES.DELETE_REQUEST):
    case FAILURE(ACTION_TYPES.ACCEPT_REQUEST):
    case FAILURE(ACTION_TYPES.DECLINE_REQUEST):
    case FAILURE(ACTION_TYPES.NOT_EXAMINED_COUNT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_REQUEST_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_REQUEST):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_REQUEST):
    case SUCCESS(ACTION_TYPES.UPDATE_REQUEST):
    case SUCCESS(ACTION_TYPES.ACCEPT_REQUEST):
    case SUCCESS(ACTION_TYPES.DECLINE_REQUEST):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_REQUEST):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case SUCCESS(ACTION_TYPES.NOT_EXAMINED_COUNT):
      return {
        ...state,
        loading: false,
        notExaminedCount: action.payload.data,
      };
    case ACTION_TYPES.LOGOUT:
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/requests';

// Actions

export const getEntities: ICrudGetAllAction<IRequest> = () => {
  const requestUrl = `${apiUrl}`;
  return {
    type: ACTION_TYPES.FETCH_REQUEST_LIST,
    payload: axios.get<IRequest>(requestUrl),
  };
};

export const getEntity: ICrudGetAction<IRequest> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_REQUEST,
    payload: axios.get<IRequest>(requestUrl),
  };
};

export const notExaminedRequestsCount: ICrudGetAllAction<number> = () => {
  const requestUrl = `${apiUrl}/not-examined-count`;
  return {
    type: ACTION_TYPES.NOT_EXAMINED_COUNT,
    payload: axios.get<number>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IRequest> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_REQUEST,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  dispatch(notExaminedRequestsCount());
  return result;
};

export const updateEntity: ICrudPutAction<IRequest> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_REQUEST,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IRequest> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_REQUEST,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  dispatch(notExaminedRequestsCount());
  return result;
};

export const acceptEntity: ICrudDeleteAction<IRequest> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}/accept`;
  const result = await dispatch({
    type: ACTION_TYPES.ACCEPT_REQUEST,
    payload: axios.post(requestUrl),
  });
  dispatch(getEntities());
  dispatch(notExaminedRequestsCount());
  return result;
};

export const declineEntity: ICrudDeleteAction<IRequest> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}/decline`;
  const result = await dispatch({
    type: ACTION_TYPES.DECLINE_REQUEST,
    payload: axios.post(requestUrl),
  });
  dispatch(getEntities());
  dispatch(notExaminedRequestsCount());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});

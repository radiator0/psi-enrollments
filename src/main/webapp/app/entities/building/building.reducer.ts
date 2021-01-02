import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IBuilding, defaultValue } from 'app/shared/model/building.model';

export const ACTION_TYPES = {
  FETCH_BUILDING_LIST: 'building/FETCH_BUILDING_LIST',
  FETCH_BUILDING: 'building/FETCH_BUILDING',
  CREATE_BUILDING: 'building/CREATE_BUILDING',
  UPDATE_BUILDING: 'building/UPDATE_BUILDING',
  DELETE_BUILDING: 'building/DELETE_BUILDING',
  RESET: 'building/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IBuilding>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type BuildingState = Readonly<typeof initialState>;

// Reducer

export default (state: BuildingState = initialState, action): BuildingState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_BUILDING_LIST):
    case REQUEST(ACTION_TYPES.FETCH_BUILDING):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_BUILDING):
    case REQUEST(ACTION_TYPES.UPDATE_BUILDING):
    case REQUEST(ACTION_TYPES.DELETE_BUILDING):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_BUILDING_LIST):
    case FAILURE(ACTION_TYPES.FETCH_BUILDING):
    case FAILURE(ACTION_TYPES.CREATE_BUILDING):
    case FAILURE(ACTION_TYPES.UPDATE_BUILDING):
    case FAILURE(ACTION_TYPES.DELETE_BUILDING):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_BUILDING_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_BUILDING):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_BUILDING):
    case SUCCESS(ACTION_TYPES.UPDATE_BUILDING):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_BUILDING):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/buildings';

// Actions

export const getEntities: ICrudGetAllAction<IBuilding> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_BUILDING_LIST,
  payload: axios.get<IBuilding>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IBuilding> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_BUILDING,
    payload: axios.get<IBuilding>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IBuilding> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_BUILDING,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IBuilding> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_BUILDING,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IBuilding> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_BUILDING,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});

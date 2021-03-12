import { Injectable } from '@angular/core';
import { InjectableRxStompConfig, RxStompService } from '@stomp/ng2-stompjs';
import { WebSocketService } from '../services/websocketService';
import { WebSocketOptions } from '../models/websocket.option';

export const progressStompConfig: InjectableRxStompConfig = {
  webSocketFactory: () => {
    return new WebSocket('ws://localhost:9002/ws');
  }
};

@Injectable()
export class ProgressWebsocketService extends WebSocketService {
  constructor(stompService: RxStompService) {
    super(
      stompService,
      progressStompConfig,
      new WebSocketOptions('/topic/request')
    );
  }
}

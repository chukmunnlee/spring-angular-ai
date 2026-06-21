import {HttpClient} from '@angular/common/http';
import { inject, Service } from '@angular/core';
import {ChatRequest, ChatResponse} from './models';
import {catchError, map, Observable, of} from 'rxjs';
import {ulid} from 'ulid';

const CONVERSATION_ID = 'CONVERSATION_ID'
const CREATED_ON = 'CREATED_ON'

@Service()
export class ChatService {
  private readonly API = '/api/memory/chat'

  private readonly httpClient = inject(HttpClient)
  public readonly conversationId: string;
  public readonly conversationDate: string;

  constructor() {

    let id = localStorage.getItem(CONVERSATION_ID)
    if (!id) {
      this.conversationId = ulid()
      this.conversationDate = (new Date()).toString()
      localStorage.setItem(CONVERSATION_ID, this.conversationId)
      localStorage.setItem(CREATED_ON, this.conversationDate)
      return
    }

    this.conversationId = id
    this.conversationDate = localStorage.getItem(CREATED_ON) ?? (new Date()).toString()
  }

  sendChatMessage(prompt: string): Observable<ChatResponse> {
    const chatRequest: ChatRequest = { prompt, conversationId: this.conversationId }
    return this.httpClient.post<ChatResponse>(this.API, chatRequest)
        .pipe(
          map(resp => ({ ...resp, isBot: true, isError: false })),
          catchError((err: any) => of({
            response: `Chat failed. Error ${JSON.stringify(err)}`,
            isBot: true, isError: true })
          )
        )
  }

}

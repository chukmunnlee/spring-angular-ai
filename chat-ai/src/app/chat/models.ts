export interface ChatRequest {
  prompt: string
  conversationId: string
}

export interface ChatResponse {
  response: string
  isBot: boolean
  isError: boolean
}

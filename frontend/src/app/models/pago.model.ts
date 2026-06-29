export interface Pago {
  id: string;
  usuario: string;
  metodo: 'PAGO_MOVIL' | 'TRANSFERENCIA';
  monto: number;
  referencia: string;
  estado?: 'PENDIENTE' | 'CONFIRMADO' | 'RECHAZADO';
  razonRechazo?: string;
  nombreTitular?: string;
  bancoOrigen?: string;
  imagenComprobante?: string;
  fechaTransferencia?: string;
}
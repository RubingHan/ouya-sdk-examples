#ifndef __UI_H__
#define __UI_H__

#include "Product.h"
#include "Receipt.h"
#include "TextButton.h"
#include "TextLabel.h"

#include <string>
#include <vector>

class CallbacksFetchGamerUUID;
class CallbacksRequestProducts;
class CallbacksRequestPurchase;
class CallbacksRequestReceipts;

class UI
{
public:
	
	UI();

	bool InitUI();

	void Destroy();

	bool HasUIChanged();

	void Render();

	void HandleInput();

	void SetGamerUUID(const std::string& gamerUUID);

	void SetMessage(const std::string& message);

	void SetDirections();

	void ClearProducts();

	void ClearReceipts();

	void AddProduct(OuyaSDK::Product product);

	void AddReceipt(OuyaSDK::Receipt receipt);

	void RegisterCallbacks();

private:

	std::vector<std::string> m_productIds;

	std::vector<OuyaSDK::Product> m_pendingProducts;
	std::vector<TextButton*> m_products;

	std::vector<OuyaSDK::Receipt> m_pendingReceipts;
	std::vector<TextButton*> m_receipts;

	TextButton* m_selectedProduct;

	bool m_uiInitialized;

	bool m_uiChanged;

	std::vector<int> m_pressed;

	TextButton m_uiRequestGamerUUID;
	TextButton m_uiRequestProducts;
	TextButton m_uiRequestPurchase;
	TextButton m_uiRequestReceipts;
	TextButton m_uiPause;
	
	TextLabel m_uiLabelFetch;
	TextLabel m_uiLabelDirections;
	TextLabel m_uiLabelMessage;

	TextButton* m_selectedButton;

	CallbacksFetchGamerUUID* m_callbacksFetchGamerUUID;
	CallbacksRequestProducts* m_callbacksRequestProducts;
	CallbacksRequestPurchase* m_callbacksRequestPurchase;
	CallbacksRequestReceipts* m_callbacksRequestReceipts;

	void RenderThreadInitProducts();
	void RenderThreadInitReceipts();

	bool ButtonReleased(int keyCode);
};

#endif